package uk.ac.glasgow.beaconchat.models;

import org.joda.time.DateTime;

public class Message {

	private int id;
	private String text;
	private DateTime dt;

	public Message(int id, String text, DateTime dt) {
		super();
		this.id = id;
		this.text = text;
		this.dt = dt;
	}

	public Message() {
		super();
		this.id = 0;
		this.text = "";
		this.dt = new DateTime();
		;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public DateTime getDt() {
		return dt;
	}

	public void setDt(DateTime dt) {
		this.dt = dt;
	}

}
