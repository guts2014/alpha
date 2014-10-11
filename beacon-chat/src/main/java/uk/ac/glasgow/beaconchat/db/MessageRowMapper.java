package uk.ac.glasgow.beaconchat.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import uk.ac.glasgow.beaconchat.models.Message;

@SuppressWarnings("rawtypes")
public class MessageRowMapper implements RowMapper	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Message msg = new Message(rs.getInt("id"),
					rs.getString("text"), new DateTime(rs.getDate("timestamp")));
			return msg;
		}
}