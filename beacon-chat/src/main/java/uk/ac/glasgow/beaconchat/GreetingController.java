package uk.ac.glasgow.beaconchat;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GreetingController {
	ObjectMapper mapper = new ObjectMapper(); 
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public Greeting greeting(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template,
				name));
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		return "index";
	}
	
	@RequestMapping(value = "/greetback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Greeting greetBack(@RequestBody Greeting greet) throws JsonParseException, JsonMappingException, IOException {
		return greet;
	}
}
