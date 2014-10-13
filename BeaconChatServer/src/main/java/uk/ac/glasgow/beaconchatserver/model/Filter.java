package uk.ac.glasgow.beaconchatserver.model;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter {
	private DateTime since;
	private int from;
	private int to;

	public Filter(DateTime since, int from, int to) {
		super();
		this.since = since;
		this.from = from;
		this.to = to;
	}

	public Filter() {
		super();
		this.since = new DateTime().minusSeconds(60);
	}

	public DateTime getSince() {
		return since;
	}

	public void setSince(DateTime since) {
		this.since = since;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

}
