package uk.ac.glasgow.beaconchat.models;

public class Greeting {
	private long id;
    private String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
    
    public Greeting() {
    	id = 0;
    	content = "";
    }

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    
}
