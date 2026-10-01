package com.devpulse.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Column;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String githubId;
	private String githubUsername;
	
	private String name;
	private String email;
	private String avatarUrl;
	private String bio;
	
	private String accessToken;
	@Column(unique = true)
	private String publicProfileSlug;
	
	private LocalDateTime createdAt;
	private LocalDateTime lastSyncedAt;
	
	@PrePersist
	protected void onCreate() {
	    createdAt = LocalDateTime.now();
	}
	
	
}
