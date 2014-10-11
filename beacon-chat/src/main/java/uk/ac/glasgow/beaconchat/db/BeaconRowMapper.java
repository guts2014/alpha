package uk.ac.glasgow.beaconchat.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uk.ac.glasgow.beaconchat.models.Beacon;

@SuppressWarnings("rawtypes")
public class BeaconRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Beacon beacon = new Beacon(rs.getString("id"),
				rs.getString("name"));
		return beacon;
	}

}
