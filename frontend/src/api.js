
const API_BASE_URL = 'http://localhost:8080/api';

async function request(url, options = {}) {
  const response = await fetch(url, options);

  if (!response.ok) {
    const message = await response.text();
    throw new Error(
      message || `Request failed with status ${response.status}`
    );
  }

  // DELETE endpoints often return 204 No Content.
  if (response.status === 204) {
    return null;
  }

  const text = await response.text();
  return text ? JSON.parse(text) : null;
}

export async function getProjects() {
  return request(`${API_BASE_URL}/projects`);
}

export async function createProject(project) {
  return request(`${API_BASE_URL}/projects`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(project),
  });
}

export async function updateProject(id, project) {
  return request(`${API_BASE_URL}/projects/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(project),
  });
}

export async function deleteProject(id) {
  return request(`${API_BASE_URL}/projects/${id}`, {
    method: 'DELETE',
  });
}

export async function getTasks() {
  return request(`${API_BASE_URL}/tasks`);
}

export async function getPhases() {
  return request(`${API_BASE_URL}/phases`);
}
