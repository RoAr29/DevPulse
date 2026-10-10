package com.devpulse.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devpulse.core.model.Phase;
import com.devpulse.core.repository.PhaseRepository;
import com.devpulse.core.exception.ResourceNotFoundException;

@Service
public class PhaseService {

    private final PhaseRepository phaseRepository;

    public PhaseService(PhaseRepository phaseRepository) {
        this.phaseRepository = phaseRepository;
    }

    public Phase createPhase(Phase phase) {
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
        		.orElseThrow(() -> new ResourceNotFoundException("Phase not found"));

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
}