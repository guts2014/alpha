package uk.ac.glasgow.beaconchat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.glasgow.beaconchat.db.MessageRowMapper;
import uk.ac.glasgow.beaconchat.models.Answer;
import uk.ac.glasgow.beaconchat.models.Beacon;
import uk.ac.glasgow.beaconchat.models.Greeting;
import uk.ac.glasgow.beaconchat.models.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Controller {
	private ObjectMapper mapper = new ObjectMapper(); 
	private final AtomicLong counter = new AtomicLong();
	private final String INSERT_MSG = "";
	private final String LOG_IN = "";
	private final String GET_BEACON_NAME = "";
	
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
	public Greeting greetBack(@RequestBody Greeting greet, HttpServletResponse response) {
		String sql = "INSERT INTO Message (text) VALUES (?)";
		jdbcTemplate.update(sql, new Object[] {greet.getContent()});
		/*
		 *if (error){
		 *	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		 *}
		 */
		return greet;
	}
	
	@Transactional
	public int sendMessage(final Message msg){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
			    new PreparedStatementCreator() {
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps =
			                connection.prepareStatement(INSERT_MSG, new String[] {"id"});
			            ps.setString(1, msg.getText());
			            return ps;
			        }
			    },
			    keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	@Transactional
	public int logInUser(String user, String deviceId){
		int id;
		try {
			jdbcTemplate.update(LOG_IN, new Object[] {user, deviceId});
			id = 0;
		} catch (DataAccessException e){
			id = jdbcTemplate.queryForObject("", Integer.class);
		}
		
		return id;
	}
	
	@Transactional
	@RequestMapping(value = "/names", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Beacon> checkBeacons(ArrayList<Beacon> beacons){
		HashMap<String, Beacon> result = new HashMap<String, Beacon>();
		for (Beacon beacon : beacons){
			String id = beacon.getId();
			String name = jdbcTemplate.queryForObject(GET_BEACON_NAME, String.class);//jdbcTemplate get name
			if (name != null){
				beacon.setName(name);
				result.put(id, beacon);
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public Answer checkBeacons(HttpServletResponse response){
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return new Answer("error", "woops, something went wrong");
	}
}
