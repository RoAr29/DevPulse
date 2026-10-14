package com.devpulse.core.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.devpulse.core.dto.PhaseResponse;

import com.devpulse.core.model.Phase;
import com.devpulse.core.service.PhaseService;

@RestController
@RequestMapping("/api/phases")
public class PhaseController {

    private final PhaseService phaseService;

    public PhaseController(PhaseService phaseService) {
        this.phaseService = phaseService;
    }

    @GetMapping
    public List<PhaseResponse> getAllPhases() {
        return phaseService.getAllPhaseResponses();
    }

    @GetMapping("/{id}")
    public PhaseResponse getPhaseById(@PathVariable Long id) {
        return phaseService.getPhaseResponseById(id);
    }

    @PostMapping
    public Phase createPhase(@Valid @RequestBody Phase phase) {
        return phaseService.createPhase(phase);
    }

    @PutMapping("/{id}")
    public Phase updatePhase(
            @PathVariable Long id,
            @Valid @RequestBody Phase phase) {
        return phaseService.updatePhase(id, phase);
    }

    @DeleteMapping("/{id}")
    public void deletePhase(@PathVariable Long id) {
        phaseService.deletePhase(id);
    }
}