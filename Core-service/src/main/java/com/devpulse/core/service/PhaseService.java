package com.devpulse.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devpulse.core.model.Phase;
import com.devpulse.core.repository.PhaseRepository;
import com.devpulse.core.exception.ResourceNotFoundException;
import com.devpulse.core.repository.ProjectRepository;
import com.devpulse.core.dto.PhaseResponse;

@Service
public class PhaseService {
	
	private final ProjectRepository projectRepository;
    private final PhaseRepository phaseRepository;

    public PhaseService(
            PhaseRepository phaseRepository,
            ProjectRepository projectRepository) {

        this.phaseRepository = phaseRepository;
        this.projectRepository = projectRepository;
    }


public Phase createPhase(Phase phase) {

    Long projectId = phase.getProject() != null
            ? phase.getProject().getId()
            : null;

    if (projectId == null || !projectRepository.existsById(projectId)) {
        throw new ResourceNotFoundException("Project not found");
    }

    return phaseRepository.save(phase);
}


    public List<Phase> getAllPhases() {
        return phaseRepository.findAll();
    }

    public Optional<Phase> getPhaseById(Long id) {
        return phaseRepository.findById(id);
    }


public Phase updatePhase(Long id, Phase updatedPhase) {

    Phase existingPhase = phaseRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Phase not found"));

    if (updatedPhase.getProject() != null) {
        Long projectId = updatedPhase.getProject().getId();

        if (projectId == null || !projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found");
        }

        existingPhase.setProject(updatedPhase.getProject());
    }

    existingPhase.setName(updatedPhase.getName());
    existingPhase.setDescription(updatedPhase.getDescription());
    existingPhase.setStatus(updatedPhase.getStatus());

    return phaseRepository.save(existingPhase);
}


    public void deletePhase(Long id) {

    	if (!phaseRepository.existsById(id)) {
    	    throw new ResourceNotFoundException("Phase not found");
    	}

        phaseRepository.deleteById(id);
    }
    

private PhaseResponse toPhaseResponse(Phase phase) {
    return new PhaseResponse(
        phase.getId(),
        phase.getName(),
        phase.getDescription(),
        phase.getStatus() == null
            ? null
            : phase.getStatus().toString(),
        phase.getProject() != null
            ? phase.getProject().getId()
            : null,
        phase.getProject() != null
            ? phase.getProject().getName()
            : null
    );
}


public List<PhaseResponse> getAllPhaseResponses() {
    return phaseRepository.findAll()
            .stream()
            .map(this::toPhaseResponse)
            .toList();
}

public PhaseResponse getPhaseResponseById(Long id) {
    Phase phase = phaseRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Phase not found"));

    return toPhaseResponse(phase);
}


}