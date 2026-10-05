package com.devpulse.githubsync.controller;

import com.devpulse.githubsync.dto.GitHubRepositoryResponse;
import com.devpulse.githubsync.service.GitHubApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubTestController {

    private final GitHubApiService githubApiService;

    public GitHubTestController(GitHubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }

    @GetMapping("/github/test")
    public GitHubRepositoryResponse[] testGitHubApi() {
        return githubApiService.getRepositories();
    }
}