package com.devpulse.core.service;

import org.springframework.stereotype.Service;

import com.devpulse.core.repository.ProjectRepository;
import com.devpulse.core.model.Project;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }
    
}