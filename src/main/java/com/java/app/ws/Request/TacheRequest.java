package com.java.app.ws.Request;

public class TacheRequest {
	/**
	 * 
	 */

	private String statut;
	private String description ;

	public TacheRequest() {
		super();

	}
	public TacheRequest(String statut, String description) {

		
		this.statut = statut;
		this.description = description;


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


}
