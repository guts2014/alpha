package uk.ac.glasgow.beaconchat;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.glasgow.beaconchat.db.MessageRowMapper;
import uk.ac.glasgow.beaconchat.models.Greeting;
import uk.ac.glasgow.beaconchat.models.Message;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GreetingController {
	private ObjectMapper mapper = new ObjectMapper(); 
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public Greeting greeting(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		/*return new Greeting(counter.incrementAndGet(), String.format(template,
				name));*/
		String sql = "SELECT text, id, time FROM Message WHERE id = ?";
		System.out.println(sql);
		Message msg = (Message) jdbcTemplate.queryForObject(sql, new Object[] { 1 }, new MessageRowMapper());
		return new Greeting(counter.incrementAndGet(), msg.getText());
				
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		return "index";
	}
	
	@RequestMapping(value = "/greetback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Greeting greetBack(@RequestBody Greeting greet) throws JsonParseException, JsonMappingException, IOException {
		String sql = "INSERT INTO Message (text) VALUES (?)";
		jdbcTemplate.update(sql, new Object[] {greet.getContent()});
		return greet;
	}
}
