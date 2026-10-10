package com.devpulse.core.service;

import org.springframework.stereotype.Service;

import com.devpulse.core.model.Project;
import com.devpulse.core.repository.ProjectRepository;
import java.util.List;
import java.util.Optional;
import com.devpulse.core.exception.ResourceNotFoundException;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }
    
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
    
    public Project updateProject(Long id, Project updatedProject) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        existingProject.setName(updatedProject.getName());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setGithubRepoUrl(updatedProject.getGithubRepoUrl());
        existingProject.setStatus(updatedProject.getStatus());

        return projectRepository.save(existingProject);
    }
    
    public void deleteProject(Long id) {
    	if (!projectRepository.existsById(id)) {
    	    throw new ResourceNotFoundException("Project not found");
    	}

        projectRepository.deleteById(id);
    }
}