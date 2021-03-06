package uk.ac.glasgow.beaconchatserver.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uk.ac.glasgow.beaconchatserver.model.Beacon;

public class BeaconRowMapper implements RowMapper<Beacon> {
	public Beacon mapRow(ResultSet rs, int rowNum) throws SQLException {
		Beacon beacon = new Beacon(rs.getString("id"), rs.getString("name"));
		return beacon;
	}

}
