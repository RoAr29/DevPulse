package com.devpulse.githubsync.service;

import com.devpulse.githubsync.dto.GitHubRepositoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
}