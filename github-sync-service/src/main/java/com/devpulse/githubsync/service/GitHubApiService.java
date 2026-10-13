package com.devpulse.githubsync.service;

import com.devpulse.githubsync.dto.GitHubPullRequestResponse;
import com.devpulse.githubsync.dto.GitHubRepositoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.devpulse.githubsync.dto.GitHubCommitResponse;

@Service
public class GitHubApiService {

    private final RestClient githubRestClient;

    public GitHubApiService(RestClient githubRestClient) {
        this.githubRestClient = githubRestClient;
    }

    public GitHubRepositoryResponse[] getRepositories() {
        return githubRestClient.get()
                .uri("/user/repos")
                .retrieve()
                .body(GitHubRepositoryResponse[].class);
    }

    public GitHubCommitResponse[] getCommits(String owner, String repo) {
        return githubRestClient.get()
                .uri("/repos/{owner}/{repo}/commits", owner, repo)
                .retrieve()
                .body(GitHubCommitResponse[].class);
    }

    public GitHubPullRequestResponse[] getPullRequests(String owner, String repo) {
        return githubRestClient.get()
                .uri("/repos/{owner}/{repo}/pulls?state=all", owner, repo)
                .retrieve()
                .body(GitHubPullRequestResponse[].class);
    }
}