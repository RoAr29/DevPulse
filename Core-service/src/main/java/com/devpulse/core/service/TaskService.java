package com.devpulse.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devpulse.core.model.Task;
import com.devpulse.core.repository.TaskRepository;
import com.devpulse.core.exception.ResourceNotFoundException;
import com.devpulse.core.repository.PhaseRepository;
import com.devpulse.core.dto.TaskResponse;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final PhaseRepository phaseRepository;

    public TaskService(
            TaskRepository taskRepository,
            PhaseRepository phaseRepository) {
        this.taskRepository = taskRepository;
        this.phaseRepository = phaseRepository;
    }


public Task createTask(Task task) {

    Long phaseId = task.getPhase() != null
            ? task.getPhase().getId()
            : null;

    if (phaseId == null || !phaseRepository.existsById(phaseId)) {
        throw new ResourceNotFoundException("Phase not found");
    }

    return taskRepository.save(task);
}


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }


public Task updateTask(Long id, Task updatedTask) {

    Task existingTask = taskRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Task not found"));

    if (updatedTask.getPhase() != null) {
        Long phaseId = updatedTask.getPhase().getId();

        if (phaseId == null || !phaseRepository.existsById(phaseId)) {
            throw new ResourceNotFoundException("Phase not found");
        }

        existingTask.setPhase(updatedTask.getPhase());
    }

    existingTask.setTitle(updatedTask.getTitle());
    existingTask.setDescription(updatedTask.getDescription());
    existingTask.setStatus(updatedTask.getStatus());
    existingTask.setPriority(updatedTask.getPriority());

    return taskRepository.save(existingTask);
}


    public void deleteTask(Long id) {

    	if (!taskRepository.existsById(id)) {
    	    throw new ResourceNotFoundException("Task not found");
    	}

        taskRepository.deleteById(id);
    }
    

private TaskResponse toTaskResponse(Task task) {
    return new TaskResponse(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getStatus() == null
            ? null
            : task.getStatus().toString(),
        task.getPriority() == null
            ? null
            : task.getPriority().toString(),
        task.getPhase() != null
            ? task.getPhase().getId()
            : null,
        task.getPhase() != null
            ? task.getPhase().getName()
            : null
    );
}


public List<TaskResponse> getAllTaskResponses() {
    return taskRepository.findAll()
            .stream()
            .map(this::toTaskResponse)
            .toList();
}

public TaskResponse getTaskResponseById(Long id) {
    Task task = taskRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Task not found"));

    return toTaskResponse(task);
}


}