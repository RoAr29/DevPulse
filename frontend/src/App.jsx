
import { useCallback, useEffect, useState } from 'react';
import {
  getProjects,
  createProject,
  updateProject,
  deleteProject,
  getTasks,
  getPhases,
} from './api';
import './App.css';

const EMPTY_FORM = {
  name: '',
  description: '',
  githubRepoUrl: '',
  status: 'IN_PROGRESS',
};

function normalizeStatus(status) {
  return (status || 'TODO').toUpperCase().replaceAll(' ', '_');
}

function App() {
  const [page, setPage] = useState('dashboard');

  const [projects, setProjects] = useState([]);
  const [loadingProjects, setLoadingProjects] = useState(true);
  const [projectsError, setProjectsError] = useState('');
  const [search, setSearch] = useState('');

  const [tasks, setTasks] = useState([]);
  const [phases, setPhases] = useState([]);
  const [loadingTasks, setLoadingTasks] = useState(true);
  const [tasksError, setTasksError] = useState('');

  const [showForm, setShowForm] = useState(false);
  const [editingProject, setEditingProject] = useState(null);
  const [form, setForm] = useState(EMPTY_FORM);
  const [saving, setSaving] = useState(false);
  const [formError, setFormError] = useState('');
  const [actionError, setActionError] = useState('');

  const loadProjects = useCallback(async () => {
    setLoadingProjects(true);
    setProjectsError('');

    try {
      const data = await getProjects();
      setProjects(Array.isArray(data) ? data : []);
    } catch (error) {
      setProjectsError(error.message || 'Could not load projects.');
    } finally {
      setLoadingProjects(false);
    }
  }, []);

  const loadDashboardData = useCallback(async () => {
    setLoadingTasks(true);
    setTasksError('');

    try {
      const [taskData, phaseData] = await Promise.all([
        getTasks(),
        getPhases(),
      ]);

      setTasks(Array.isArray(taskData) ? taskData : []);
      setPhases(Array.isArray(phaseData) ? phaseData : []);
    } catch (error) {
      setTasksError(error.message || 'Could not load dashboard tasks.');
    } finally {
      setLoadingTasks(false);
    }
  }, []);

  useEffect(() => {
    loadProjects();
    loadDashboardData();
  }, [loadProjects, loadDashboardData]);

  function openCreateForm() {
    setEditingProject(null);
    setForm(EMPTY_FORM);
    setFormError('');
    setActionError('');
    setShowForm(true);
  }

  function openEditForm(project) {
    setEditingProject(project);
    setForm({
      name: project.name || '',
      description: project.description || '',
      githubRepoUrl: project.githubRepoUrl || '',
      status: project.status || 'IN_PROGRESS',
    });
    setFormError('');
    setActionError('');
    setShowForm(true);
  }

  function closeForm() {
    if (saving) return;

    setShowForm(false);
    setEditingProject(null);
    setForm(EMPTY_FORM);
    setFormError('');
  }

  function handleFormChange(event) {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  }

  async function handleFormSubmit(event) {
    event.preventDefault();
    setFormError('');

    if (!form.name.trim()) {
      setFormError('Please enter a project name.');
      return;
    }

    if (form.githubRepoUrl.trim()) {
      try {
        new URL(form.githubRepoUrl);
      } catch {
        setFormError('Enter a valid GitHub repository URL.');
        return;
      }
    }

    const payload = {
      name: form.name.trim(),
      description: form.description.trim(),
      githubRepoUrl: form.githubRepoUrl.trim(),
      status: form.status,
    };

    setSaving(true);

    try {
      if (editingProject) {
        await updateProject(editingProject.id, payload);
      } else {
        await createProject(payload);
      }

      setShowForm(false);
      setEditingProject(null);
      setForm(EMPTY_FORM);
      await loadProjects();
    } catch (error) {
      setFormError(error.message || 'Could not save the project.');
    } finally {
      setSaving(false);
    }
  }

  async function handleDelete(project) {
    const confirmed = window.confirm(
      `Delete "${project.name}"? This action cannot be undone.`
    );

    if (!confirmed) return;

    setActionError('');

    try {
      await deleteProject(project.id);
      await loadProjects();
    } catch (error) {
      setActionError(error.message || 'Could not delete the project.');
    }
  }

  const filteredProjects = projects.filter((project) => {
    const searchText = search.toLowerCase();

    return (
      (project.name || '').toLowerCase().includes(searchText) ||
      (project.description || '').toLowerCase().includes(searchText) ||
      (project.status || '').toLowerCase().includes(searchText)
    );
  });

  const completedProjects = projects.filter(
    (project) => normalizeStatus(project.status) === 'COMPLETED'
  ).length;

  const activeTasks = tasks.filter(
    (task) => normalizeStatus(task.status) === 'IN_PROGRESS'
  ).length;

  const completedTasks = tasks.filter((task) =>
    ['COMPLETED', 'DONE'].includes(normalizeStatus(task.status))
  ).length;

  const phaseProjectNames = Object.fromEntries(
    phases.map((phase) => [
      String(phase.id),
      phase.projectName ||
        projects.find((project) => String(project.id) === String(phase.projectId))
          ?.name ||
        'Unknown project',
    ])
  );

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div className="brand">
          <div className="brand-icon">D</div>
          <span>DevPulse</span>
        </div>

        <p className="nav-label">WORKSPACE</p>

        <nav className="navigation">
          <button
            className={`nav-item ${page === 'dashboard' ? 'active' : ''}`}
            onClick={() => setPage('dashboard')}
          >
            ▦ Dashboard
          </button>

          <button
            className={`nav-item ${page === 'projects' ? 'active' : ''}`}
            onClick={() => setPage('projects')}
          >
            ▣ Projects
          </button>

          <button
            className="nav-item"
            onClick={() =>
              document.getElementById('tasks')?.scrollIntoView({
                behavior: 'smooth',
              })
            }
          >
            ✓ Tasks
          </button>

          <button
            className="nav-item"
            onClick={() => setActionError('Activity history is coming next.')}
          >
            ⌁ Activity
          </button>
        </nav>

        <div className="sidebar-footer">
          <div className="avatar">DP</div>
          <div>
            <strong>Developer</strong>
            <span>My workspace</span>
          </div>
        </div>
      </aside>

      <main className="main-content">
        <header className="topbar">
          <div>
            <p className="eyebrow">
              WORKSPACE / {page === 'dashboard' ? 'OVERVIEW' : 'PROJECTS'}
            </p>
            <h1>{page === 'dashboard' ? 'Dashboard' : 'Projects'}</h1>
          </div>

          <div className="topbar-badge">
            <span className="online-dot" />
            DevPulse workspace
          </div>
        </header>

        {page === 'dashboard' ? (
          <>
            <section className="welcome">
              <div>
                <p className="eyebrow">YOUR DEVELOPMENT HUB</p>
                <h2>Build better. Track smarter.</h2>
                <p>
                  All your projects, tasks, and development progress in one place.
                </p>
              </div>
              <div className="welcome-symbol">✳</div>
            </section>

            <section className="stats-grid">
              <StatCard
                label="Total Projects"
                value={String(projects.length).padStart(2, '0')}
                detail="Across your workspace"
                icon="▣"
              />

              <StatCard
                label="Active Tasks"
                value={String(activeTasks).padStart(2, '0')}
                detail="Keep the momentum"
                icon="◷"
              />

              <StatCard
                label="Completed Tasks"
                value={String(completedTasks).padStart(2, '0')}
                detail="Progress is progress"
                icon="✓"
              />

              <StatCard
                label="Total Tasks"
                value={String(tasks.length).padStart(2, '0')}
                detail="Across all project phases"
                icon="↗"
              />
            </section>

            <section className="section">
              <div className="section-heading">
                <div>
                  <h2>Your Projects</h2>
                  <p>Keep track of what you're building.</p>
                </div>

                <button
                  className="primary-button"
                  onClick={() => {
                    setPage('projects');
                    openCreateForm();
                  }}
                >
                  + New project
                </button>
              </div>

              <ProjectGrid
                projects={projects.slice(0, 3)}
                loading={loadingProjects}
                error={projectsError}
                onEdit={openEditForm}
                onDelete={handleDelete}
              />

              <button
                className="text-link projects-view-all"
                onClick={() => setPage('projects')}
              >
                View all projects →
              </button>
            </section>

            <section className="section" id="tasks">
              <div className="section-heading">
                <div>
                  <h2>Recent Tasks</h2>
                  <p>A quick look at your team's work.</p>
                </div>

                <button
                  className="secondary-button"
                  onClick={loadDashboardData}
                  disabled={loadingTasks}
                >
                  {loadingTasks ? 'Refreshing...' : '↻ Refresh'}
                </button>
              </div>

              <div className="task-list">
                {loadingTasks ? (
                  <p className="loading-state">Loading tasks...</p>
                ) : tasksError ? (
                  <div className="error-state" role="alert">
                    <h3>Could not load tasks</h3>
                    <p>{tasksError}</p>
                    <button
                      className="secondary-button"
                      onClick={loadDashboardData}
                    >
                      Try again
                    </button>
                  </div>
                ) : tasks.length === 0 ? (
                  <div className="empty-state">
                    <h3>No tasks yet</h3>
                    <p>
                      Create tasks in your project phases to see them here.
                    </p>
                  </div>
                ) : (
                  tasks.slice(0, 5).map((task) => {
                    const status = normalizeStatus(task.status);
                    const isCompleted = ['COMPLETED', 'DONE'].includes(status);
                    const statusLabel = status.replaceAll('_', ' ');
                    const statusClass = status.toLowerCase().replaceAll('_', '-');

                    return (
                      <div className="task-row" key={task.id}>
                        <div className={`task-check ${isCompleted ? 'checked' : ''}`}>
                          {isCompleted ? '✓' : ''}
                        </div>

                        <div className="task-info">
                          <strong>{task.title || task.name || 'Untitled task'}</strong>
                          <span>
                            {phaseProjectNames[String(task.phaseId)] ||
                              task.phaseName ||
                              'Project'}
                          </span>
                        </div>

                        <span className={`task-status task-${statusClass}`}>
                          {statusLabel}
                        </span>
                      </div>
                    );
                  })
                )}
              </div>
            </section>
          </>
        ) : (
          <section className="section projects-page">
            <div className="section-heading projects-page-heading">
              <div>
                <h2>All Projects</h2>
                <p>Create, organize, and manage your development work.</p>
              </div>

              <button className="primary-button" onClick={openCreateForm}>
                + New project
              </button>
            </div>

            <div className="projects-toolbar">
              <input
                className="project-search"
                type="search"
                placeholder="Search projects by name, description, or status..."
                value={search}
                onChange={(event) => setSearch(event.target.value)}
                aria-label="Search projects"
              />

              <button
                className="secondary-button"
                onClick={loadProjects}
                disabled={loadingProjects}
              >
                {loadingProjects ? 'Refreshing...' : '↻ Refresh'}
              </button>
            </div>

            {actionError && (
              <p className="error-message" role="alert">
                {actionError}
              </p>
            )}

            <ProjectGrid
              projects={filteredProjects}
              loading={loadingProjects}
              error={projectsError}
              onEdit={openEditForm}
              onDelete={handleDelete}
            />

            {!loadingProjects &&
              !projectsError &&
              filteredProjects.length === 0 && (
                <div className="empty-state">
                  <h3>{search ? 'No matching projects' : 'No projects yet'}</h3>
                  <p>
                    {search
                      ? 'Try another search term.'
                      : 'Create your first project to get started.'}
                  </p>
                  {!search && (
                    <button className="primary-button" onClick={openCreateForm}>
                      + Create your first project
                    </button>
                  )}
                </div>
              )}
          </section>
        )}

        <footer className="footer">
          DevPulse <span>·</span> Your progress, in one place.
        </footer>
      </main>

      {showForm && (
        <div
          className="modal-backdrop"
          onMouseDown={(event) => {
            if (event.target === event.currentTarget) closeForm();
          }}
        >
          <section
            className="project-modal"
            role="dialog"
            aria-modal="true"
            aria-labelledby="project-form-title"
          >
            <div className="modal-heading">
              <div>
                <p className="eyebrow">
                  {editingProject ? 'UPDATE YOUR WORK' : 'START SOMETHING NEW'}
                </p>
                <h2 id="project-form-title">
                  {editingProject ? 'Edit project' : 'New project'}
                </h2>
              </div>

              <button
                type="button"
                className="modal-close"
                onClick={closeForm}
                disabled={saving}
                aria-label="Close form"
              >
                ×
              </button>
            </div>

            <form onSubmit={handleFormSubmit}>
              <div className="form-group">
                <label htmlFor="project-name">Project name *</label>
                <input
                  id="project-name"
                  name="name"
                  value={form.name}
                  onChange={handleFormChange}
                  placeholder="e.g. DevPulse"
                  maxLength={120}
                  required
                  autoFocus
                />
              </div>

              <div className="form-group">
                <label htmlFor="project-description">Description</label>
                <textarea
                  id="project-description"
                  name="description"
                  value={form.description}
                  onChange={handleFormChange}
                  placeholder="What are you building?"
                  rows={4}
                />
              </div>

              <div className="form-group">
                <label htmlFor="project-github">GitHub repository URL</label>
                <input
                  id="project-github"
                  name="githubRepoUrl"
                  type="url"
                  value={form.githubRepoUrl}
                  onChange={handleFormChange}
                  placeholder="https://github.com/username/repository"
                />
              </div>

              <div className="form-group">
                <label htmlFor="project-status">Status</label>
                <select
                  id="project-status"
                  name="status"
                  value={form.status}
                  onChange={handleFormChange}
                >
                  <option value="IN_PROGRESS">In Progress</option>
                  <option value="PLANNING">Planning</option>
                  <option value="COMPLETED">Completed</option>
                  <option value="ON_HOLD">On Hold</option>
                </select>
              </div>

              {formError && (
                <p className="error-message" role="alert">
                  {formError}
                </p>
              )}

              <div className="form-actions">
                <button
                  type="button"
                  className="cancel-button"
                  onClick={closeForm}
                  disabled={saving}
                >
                  Cancel
                </button>

                <button
                  type="submit"
                  className="primary-button"
                  disabled={saving}
                >
                  {saving
                    ? 'Saving...'
                    : editingProject
                      ? 'Save changes'
                      : 'Create project'}
                </button>
              </div>
            </form>
          </section>
        </div>
      )}
    </div>
  );
}

function ProjectGrid({ projects, loading, error, onEdit, onDelete }) {
  if (loading) {
    return <div className="loading-state">Loading projects...</div>;
  }

  if (error) {
    return (
      <div className="error-state" role="alert">
        <h3>Could not load projects</h3>
        <p>{error}</p>
      </div>
    );
  }

  if (projects.length === 0) {
    return null;
  }

  const colors = ['purple', 'blue', 'orange'];

  return (
    <div className="project-grid">
      {projects.map((project, index) => {
        const color = colors[index % colors.length];
        const statusClass = (project.status || '')
          .toLowerCase()
          .replace(/[^a-z0-9]+/g, '-');

        return (
          <article className="project-card" key={project.id}>
            <div className="project-card-top">
              <div className={`project-icon ${color}`}>▣</div>
              <span className={`status-badge status-${statusClass}`}>
                {(project.status || 'NOT SPECIFIED').replaceAll('_', ' ')}
              </span>
            </div>

            <h3>{project.name}</h3>

            <p className="project-description">
              {project.description || 'No description provided.'}
            </p>

            {project.githubRepoUrl ? (
              <a
                className="text-link"
                href={project.githubRepoUrl}
                target="_blank"
                rel="noreferrer"
              >
                View GitHub repository →
              </a>
            ) : (
              <p className="project-no-repo">No GitHub repository linked</p>
            )}

            <div className="project-actions">
              <button
                className="project-action-button"
                onClick={() => onEdit(project)}
              >
                ✎ Edit
              </button>

              <button
                className="project-action-button delete"
                onClick={() => onDelete(project)}
              >
                Delete
              </button>
            </div>
          </article>
        );
      })}
    </div>
  );
}

function StatCard({ label, value, detail, icon }) {
  return (
    <article className="stat-card">
      <div className="stat-top">
        <span>{label}</span>
        <span className="stat-icon">{icon}</span>
      </div>
      <strong className="stat-value">{value}</strong>
      <span className="stat-detail">{detail}</span>
    </article>
  );
}

export default App;
