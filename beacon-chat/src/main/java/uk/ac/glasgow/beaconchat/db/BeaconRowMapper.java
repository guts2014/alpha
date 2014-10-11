package uk.ac.glasgow.beaconchat.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import uk.ac.glasgow.beaconchat.models.Beacon;

public class BeaconRowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Beacon msg = new Beacon(rs.getString("id"),
				rs.getString("name"));
		return msg;
	}

}
