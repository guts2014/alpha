package uk.ac.glasgow.beaconchatserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Beacon {

	private String id;
	private String name;

	public Beacon() {
		super();
	}

	public Beacon(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Beacon(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
