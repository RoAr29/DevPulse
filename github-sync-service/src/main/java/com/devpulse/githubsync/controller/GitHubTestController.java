package com.devpulse.githubsync.controller;

import com.devpulse.githubsync.dto.GitHubCommitResponse;
import com.devpulse.githubsync.dto.GitHubPullRequestResponse;
import com.devpulse.githubsync.dto.GitHubRepositoryResponse;
import com.devpulse.githubsync.service.GitHubApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


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

    @GetMapping("/github/pulls")
    public GitHubPullRequestResponse[] getPullRequests() {
        return githubApiService.getPullRequests("Venkateshpaitwar", "DevPulse");
    }

    @GetMapping("/github/languages")
    public Map<String, Long> getLanguages(
            @RequestParam String owner,
            @RequestParam String repo) {

        return githubApiService.getLanguages(owner, repo);
    }
}