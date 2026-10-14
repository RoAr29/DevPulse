
package com.devpulse.core.dto;

public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private String githubRepoUrl;
    private String status;

    public ProjectResponse() {
    }

    public ProjectResponse(Long id, String name, String description,
                           String githubRepoUrl, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.githubRepoUrl = githubRepoUrl;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGithubRepoUrl() {
        return githubRepoUrl;
    }

    public void setGithubRepoUrl(String githubRepoUrl) {
        this.githubRepoUrl = githubRepoUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
