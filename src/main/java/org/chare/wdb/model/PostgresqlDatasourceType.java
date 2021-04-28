package org.chare.wdb.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class PostgresqlDatasourceType implements JdbcDatasourceType {

	@Override
	public String getUrlTemplate() {
		return "jdbc:postgresql://{hostname}:{port}/{database}?{properties}";
	}

	@Override
	public String createUrl(Map<String, Object> properties) {
		return getUrlTemplate()
				.replace("{hostname}", String.valueOf(properties.get(HOSTNAME)))
				.replace("{port}", String.valueOf(properties.get(PORT)))
				.replace("{database}", String.valueOf(properties.get(DATABASE)))
				.replace("{properties}", "") //FIXME convert other properties
				;
	}

	@Override
	public Map<String, Object> toProperties(String url) {
		return null; //FIXME parse properties from URL
	}

	@Override
	public Connection getConnection(Map<String, Object> properties) throws SQLException {
		String url = createUrl(properties);
		String username = String.valueOf(properties.get(USERNAME));
		String password = properties.get(PASSWORD) == null ? null : String.valueOf(properties.get(PASSWORD));
		return DriverManager.getConnection(url, username, password);
	}
}
