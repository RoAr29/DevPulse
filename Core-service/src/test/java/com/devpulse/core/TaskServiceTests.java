
package com.devpulse.core;

import com.devpulse.core.exception.ResourceNotFoundException;
import com.devpulse.core.repository.TaskRepository;
import com.devpulse.core.repository.PhaseRepository;
import com.devpulse.core.service.TaskService;
import com.devpulse.core.model.Task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devpulse.core.model.Phase;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private PhaseRepository phaseRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void updateTaskWithInvalidIdThrowsException() {
        Long taskId = 999L;

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.empty());

        Task updatedTask = new Task();

        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.updateTask(taskId, updatedTask)
        );
    }
    

@Test
void createTaskWithInvalidPhaseThrowsException() {
    Task task = new Task();

    Phase phase = new Phase();
    phase.setId(999L);
    task.setPhase(phase);

    when(phaseRepository.existsById(999L))
            .thenReturn(false);

    assertThrows(
            ResourceNotFoundException.class,
            () -> taskService.createTask(task)
    );

    verify(taskRepository, never()).save(any(Task.class));
}

}
