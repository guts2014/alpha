package uk.ac.glasgow.beaconchat.models;

import java.util.ArrayList;

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
