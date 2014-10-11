package uk.ac.glasgow.beaconchat.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import uk.ac.glasgow.beaconchat.models.User;

public class UserRowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		User msg = new User(rs.getInt("id"),
				rs.getString("name"), rs.getString("email"));
		return msg;
	}

}
