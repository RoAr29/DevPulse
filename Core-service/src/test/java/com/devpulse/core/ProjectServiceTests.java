
package com.devpulse.core;

import com.devpulse.core.model.Project;
import com.devpulse.core.repository.ProjectRepository;
import com.devpulse.core.service.ProjectService;
import com.devpulse.core.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTests {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void updateProjectWithInvalidIdThrowsException() {
        Long projectId = 999L;

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.empty());

        Project updatedProject = new Project();

        assertThrows(
                ResourceNotFoundException.class,
                () -> projectService.updateProject(projectId, updatedProject)
        );
    }
}