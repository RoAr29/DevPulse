package com.devpulse.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpulse.core.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}