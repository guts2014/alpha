package uk.ac.glasgow.beaconchatserver.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Names {
	private ArrayList<Beacon> beacons;

	public Names(ArrayList<Beacon> beacons) {
		super();
		this.beacons = beacons;
	}

	public Names() {
		super();
		this.beacons = new ArrayList<Beacon>();
	}

	public ArrayList<Beacon> getBeacons() {
		return beacons;
	}

	public void setBeacons(ArrayList<Beacon> beacons) {
		this.beacons = beacons;
	}

}
