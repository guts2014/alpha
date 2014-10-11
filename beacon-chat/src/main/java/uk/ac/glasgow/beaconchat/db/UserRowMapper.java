package uk.ac.glasgow.beaconchat.db;

import java.sql.ResultSet;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uk.ac.glasgow.beaconchat.models.User;

@SuppressWarnings("rawtypes")
public class UserRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User(rs.getString("deviceID"),
				rs.getString("name"), rs.getString("email"));
		return user;
	}

}
