package com.devpulse.githubsync.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubCommitResponse(
        String sha,
        CommitDetails commit,
        GitHubUser author,
        @JsonProperty("html_url")
        String htmlUrl
) {

    public record CommitDetails(
            String message,
            CommitAuthor author
    ) {}

    public record CommitAuthor(
            String name,
            String email,
            String date
    ) {}

    public record GitHubUser(
            String login
    ) {}
}