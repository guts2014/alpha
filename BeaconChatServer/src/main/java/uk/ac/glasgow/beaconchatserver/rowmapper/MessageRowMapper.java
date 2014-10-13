package uk.ac.glasgow.beaconchatserver.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import uk.ac.glasgow.beaconchatserver.model.Message;

@SuppressWarnings("rawtypes")
public class MessageRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Message msg = new Message(rs.getInt("id"), rs.getString("text"),
				new DateTime(rs.getDate("time")));
		return msg;
	}
}