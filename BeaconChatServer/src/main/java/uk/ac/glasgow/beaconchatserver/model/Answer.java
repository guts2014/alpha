package uk.ac.glasgow.beaconchatserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Answer {
	private String status;
	private String message;

	public Answer(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public Answer(String message) {
		super();
		this.status = "error";
		this.message = message;
	}

	public Answer() {
		super();
		this.status = "ok";
		this.message = "sucessful";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
