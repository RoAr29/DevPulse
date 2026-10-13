package com.devpulse.githubsync.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubRepositoryResponse(
        Long id,
        String name,

        @JsonProperty("full_name")
        String fullName,

        @JsonProperty("private")
        Boolean isPrivate,

        @JsonProperty("html_url")
        String htmlUrl,

        String description,
        String language,

        @JsonProperty("default_branch")
        String defaultBranch
) {
}