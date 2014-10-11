package uk.ac.glasgow.beaconchat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import uk.ac.glasgow.beaconchat.db.ChatMessageRowMapper;
import uk.ac.glasgow.beaconchat.models.Answer;
import uk.ac.glasgow.beaconchat.models.Beacon;
import uk.ac.glasgow.beaconchat.models.ChatMessage;
import uk.ac.glasgow.beaconchat.models.Connect;
import uk.ac.glasgow.beaconchat.models.Messages;

@RestController
public class Controller {
	final static Logger logger = LoggerFactory.getLogger(Controller.class);

	private static final String ADD_USER_MSG = "INSERT INTO UserMessages (deviceID, name, msgID)"
			+ "VALUES (?, ?, ?)";
	private static final String ADD_BEACON_MSG = "INSERT INTO BeaconMessages (msgID, beaconid)"
			+ "VALUES (?, ?)";
	private static final String INSERT_MSG = "INSERT INTO Message (text) VALUES (?)";
	private static final String GET_BEACON_NAME = 
			"SELECT name "
			+ "FROM Beacon "
			+ "WHERE lower(id) = lower(?)";
	private static final String GET_MESSAGES = "SELECT u.deviceID, u.name, email, um.msgid, text, time, beaconID "
			+ "FROM User u, Message m, UserMessages um, Beacon b, BeaconMessages bm "
			+ "WHERE u.deviceID = um.deviceID "
			+ "AND u.name = um.name "
			+ "AND b.id = bm.beaconid "
			+ "AND m.id = um.msgid "
			+ "AND m.id = bm.msgid "
			+ "AND time > ? "
			+ "AND beacon.id = ?";
	private static final String GET_NEW_MESSAGES = "SELECT u.deviceID, u.name as username, email, um.msgid, text, time, beaconID "
			+ "FROM User u, Message m, UserMessages um, Beacon b, BeaconMessages bm "
			+ "WHERE u.deviceID = um.deviceID "
			+ "AND u.name = um.name "
			+ "AND b.id = bm.beaconid "
			+ "AND m.id = um.msgid "
			+ "AND m.id = bm.msgid "
			+ "AND m.id > ? "
			+ "AND beaconid = ?";
	private static final String ADD_USER = "INSERT INTO User (deviceID, name, email)"
			+ "VALUES (?, ?, ?)";;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		return "Hello, World! :)";
	}

	@Transactional
	@RequestMapping(value = "/send", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Answer sendMessage(@RequestBody final ChatMessage msg,
			HttpServletResponse response) {
		Answer answer = null;
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							INSERT_MSG, new String[] { "id" });
					ps.setString(1, msg.getMessage().getText());
					return ps;
				}
			}, keyHolder);
			int id = keyHolder.getKey().intValue();
			jdbcTemplate.update(ADD_USER_MSG, new Object[] {
					msg.getUser().getDeviceID(), msg.getUser().getName(), id });
			jdbcTemplate.update(ADD_BEACON_MSG, new Object[] {
					id, msg.getBeacon().getId() });
			answer = new Answer();
		} catch (DataAccessException e) {
			answer = new Answer(e.getMessage());
		}
		return answer;

	}

	@Transactional
	@RequestMapping(value = "/names", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Beacon> checkBeacons(@RequestBody ArrayList<Beacon> beacons) {
		HashMap<String, Beacon> result = new HashMap<String, Beacon>();
		for (Beacon beacon : beacons) {
			String id = beacon.getId();
			String name = jdbcTemplate.queryForObject(GET_BEACON_NAME, new Object[]{id},
					String.class);// jdbcTemplate get name
			System.out.printf("LOGGGGG %s %s %s\n",GET_BEACON_NAME, id, name);
			logger.error("LOGGGGG {} {} {}", GET_BEACON_NAME, id, name);
			if (name != null) {
				beacon.setName(name);
				result.put(id, beacon);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/connect", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<ChatMessage> connect(@RequestBody Connect connect,
			HttpServletResponse response) {
		ArrayList<ChatMessage> result;
		try {
			jdbcTemplate.update(ADD_USER, new Object[] {connect.getUser().getDeviceID(), connect.getUser().getName(), connect.getUser().getEmail()});
		} catch (DataAccessException e){
			//ignore
		}
		result = (ArrayList<ChatMessage>) jdbcTemplate.query(GET_MESSAGES, new Object[] {(new DateTime()).getMillis(), connect.getBeacon().getId()}, new ChatMessageRowMapper());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/messages", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<ChatMessage> messages(@RequestBody Messages messages, HttpServletResponse response) {
		

		ArrayList<ChatMessage> result;
		result = (ArrayList<ChatMessage>) jdbcTemplate.query(GET_NEW_MESSAGES, new Object[] {messages.getFilter().getFrom(), messages.getBeacon().getId()}, new ChatMessageRowMapper());

		return result;
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public Answer checkBeacons(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return new Answer("error", "woops, something went wrong");
	}
}
