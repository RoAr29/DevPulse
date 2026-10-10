package com.devpulse.githubsync.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubPullRequestResponse(
        Integer number,
        String title,
        String state,
        GitHubUser user,

        @JsonProperty("created_at")
        String createdAt,

        @JsonProperty("updated_at")
        String updatedAt,

        @JsonProperty("closed_at")
        String closedAt,

        @JsonProperty("merged_at")
        String mergedAt,

        @JsonProperty("html_url")
        String htmlUrl
) {
    public record GitHubUser(
            String login
    ) {}
}