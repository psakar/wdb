package org.chare.wdb.model;

import java.util.Map;

import javax.sql.DataSource;

public class PostgresqlDatasourceType implements JdbcDatasourceType {

	@Override
	public String getUrlTemplate() {
		return null;
	}

	@Override
	public String toUrl(Map<String, Object> properties) {
		return null;
	}

	@Override
	public Map<String, Object> toProperties(String url) {
		return null;
	}

	@Override
	public DataSource createDataSource(Map<String, Object> properties) {
		return null;
	}
}
