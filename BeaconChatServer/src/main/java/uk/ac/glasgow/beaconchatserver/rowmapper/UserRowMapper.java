package uk.ac.glasgow.beaconchatserver.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uk.ac.glasgow.beaconchatserver.model.User;

public class UserRowMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User(rs.getString("deviceID"), rs.getString("name"),
				rs.getString("email"));
		return user;
	}

}
