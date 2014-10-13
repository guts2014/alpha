package uk.ac.glasgow.beaconchatserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Messages {

	private User user;
	private Beacon beacon;
	private Filter filter;

	public Messages(User user, Beacon beacon, Filter filter) {
		super();
		this.user = user;
		this.beacon = beacon;
		this.filter = filter;
	}

	public Messages() {
		super();
		this.user = new User();
		this.beacon = new Beacon();
		this.filter = new Filter();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Beacon getBeacon() {
		return beacon;
	}

	public void setBeacon(Beacon beacon) {
		this.beacon = beacon;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

}
