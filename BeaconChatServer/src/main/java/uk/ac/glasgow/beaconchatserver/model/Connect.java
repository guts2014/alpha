package uk.ac.glasgow.beaconchatserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Connect {
	private Beacon beacon;
	private User user;

	public Connect(Beacon beacon, User user) {
		super();
		this.beacon = beacon;
		this.user = user;
	}

	public Connect() {
		super();
		this.beacon = new Beacon();
		this.user = new User();
	}

	public Beacon getBeacon() {
		return beacon;
	}

	public void setBeacon(Beacon beacon) {
		this.beacon = beacon;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
