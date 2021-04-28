package org.chare.wdb.model;

import static java.util.Arrays.asList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface JdbcDatasourceType {
	String TYPE = "type";
	String HOSTNAME = "hostname";
	String PORT = "port";
	String DATABASE = "database";
	String USERNAME = "username";
	String PASSWORD = "password";

	static List<DatasourcePropertyType<?>> createJdbcPropertyTypes() {
		return asList(
			new DatasourcePropertyType<>(TYPE, JdbcDatasourceType.class, true, null),
			new DatasourcePropertyType<>(HOSTNAME, String.class, true, "localhost"),
			new DatasourcePropertyType<>(PORT, Integer.class, true, 0),
			new DatasourcePropertyType<>(DATABASE, String.class, true, ""),
			new DatasourcePropertyType<>(USERNAME, String.class, true, ""),
			new DatasourcePropertyType<>(PASSWORD, String.class, false, "")
		);
	}

	String getUrlTemplate();

	String createUrl(Map<String, Object> properties);

	Map<String, Object> toProperties(String url);

	Connection getConnection(Map<String, Object> properties) throws SQLException;
}
