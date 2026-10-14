
package com.devpulse.core.dto;

public class PhaseResponse {

    private Long id;
    private String name;
    private String description;
    private String status;
    private Long projectId;
    private String projectName;

    public PhaseResponse() {
    }

    public PhaseResponse(Long id, String name, String description,
                     String status, Long projectId, String projectName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.projectId = projectId;
        this.projectName = projectName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    
    public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
