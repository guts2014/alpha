package uk.ac.glasgow.beaconchat.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import uk.ac.glasgow.beaconchat.models.Beacon;
import uk.ac.glasgow.beaconchat.models.ChatMessage;
import uk.ac.glasgow.beaconchat.models.Message;
import uk.ac.glasgow.beaconchat.models.User;

@SuppressWarnings("rawtypes")
public class ChatMessageRowMapper implements RowMapper{
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Beacon beacon = new Beacon(rs.getString("beaconID"));
		
		Message msg = new Message(rs.getInt("msgID"),
				rs.getString("text"), new DateTime(rs.getDate("time")));
		
		User user = new User(rs.getString("deviceID"),
				rs.getString("name"), rs.getString("email"));
		
		return new ChatMessage(msg, user, beacon);
	}

}
