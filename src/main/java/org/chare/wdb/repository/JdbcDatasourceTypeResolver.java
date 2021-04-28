package org.chare.wdb.repository;

import org.chare.wdb.model.JdbcDatasourceType;
import org.chare.wdb.model.PostgresqlDatasourceType;

public class JdbcDatasourceTypeResolver {

	public JdbcDatasourceType getType(String name) {
		if ("Postgresql".equalsIgnoreCase(name)) {
			return new PostgresqlDatasourceType();
		}
		throw new IllegalArgumentException("Unknown JDBC datasource type " + name);
	}
}
