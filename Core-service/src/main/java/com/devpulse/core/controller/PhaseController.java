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
    public List<Phase> getAllPhases() {
        return phaseService.getAllPhases();
    }

    @GetMapping("/{id}")
    public Phase getPhaseById(@PathVariable Long id) {
        return phaseService.getPhaseById(id)
                .orElseThrow(() -> new RuntimeException("Phase not found"));
    }

    @PostMapping
    public Phase createPhase(@RequestBody Phase phase) {
        return phaseService.createPhase(phase);
    }

    @PutMapping("/{id}")
    public Phase updatePhase(
            @PathVariable Long id,
            @RequestBody Phase phase) {

        return phaseService.updatePhase(id, phase);
    }

    @DeleteMapping("/{id}")
    public void deletePhase(@PathVariable Long id) {
        phaseService.deletePhase(id);
    }
}