package com.java.app.ws.Request;

public class TacheRequest {
	/**
	 * 
	 */

	private String statut;
	private String description ;

	private Long projectId;
	private Long userId;

	public TacheRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TacheRequest(String statut, String description, Long userId, Long projectId) {
		this.statut = statut;
		this.description = description;
		this.userId = userId;
		this.projectId = projectId;
	}


	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
