package uk.ac.glasgow.beaconchat.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataAnswer extends Answer {
	private Object data;

	public DataAnswer(String status, String message, Object data) {
		super(status, message);
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
