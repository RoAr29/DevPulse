package com.devpulse.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpulse.core.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByGithubUsername(String githubUsername);

}