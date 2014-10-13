package uk.ac.glasgow.beaconchatserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private String deviceID;
	private String name;
	private String email;

	public User() {
		super();
	}

	public User(String deviceID, String name, String email) {
		super();
		this.deviceID = deviceID;
		this.name = name;
		this.email = email;
	}

	public User(String name, String email) {
		super();
		this.deviceID = "";
		this.name = name;
		this.email = email;
	}

	public User(String name) {
		super();
		this.deviceID = "";
		this.name = name;
		this.email = "";
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
