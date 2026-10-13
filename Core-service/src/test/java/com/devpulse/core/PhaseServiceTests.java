
package com.devpulse.core;

import com.devpulse.core.exception.ResourceNotFoundException;
import com.devpulse.core.repository.PhaseRepository;
import com.devpulse.core.repository.ProjectRepository;
import com.devpulse.core.service.PhaseService;
import com.devpulse.core.model.Phase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhaseServiceTests {

    @Mock
    private PhaseRepository phaseRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private PhaseService phaseService;

    @Test
    void updatePhaseWithInvalidIdThrowsException() {
        Long phaseId = 999L;

        when(phaseRepository.findById(phaseId))
                .thenReturn(Optional.empty());

        Phase updatedPhase = new Phase();

        assertThrows(
                ResourceNotFoundException.class,
                () -> phaseService.updatePhase(phaseId, updatedPhase)
        );
    }
    

@Test
void createPhaseWithInvalidProjectThrowsException() {
    Phase phase = new Phase();

    com.devpulse.core.model.Project project =
            new com.devpulse.core.model.Project();
    project.setId(999L);
    phase.setProject(project);

    when(projectRepository.existsById(999L))
            .thenReturn(false);

    assertThrows(
            ResourceNotFoundException.class,
            () -> phaseService.createPhase(phase)
    );

    verify(phaseRepository, never()).save(Mockito.any(Phase.class));
}

}
