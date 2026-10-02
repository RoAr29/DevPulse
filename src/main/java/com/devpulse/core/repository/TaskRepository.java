package com.devpulse.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpulse.core.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}