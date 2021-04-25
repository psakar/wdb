package org.chare.wdb.model;

import java.util.Map;

import javax.sql.DataSource;

public interface JdbcDatasourceType {

	String getUrlTemplate();

	String toUrl(Map<String, Object> properties);

	Map<String, Object> toProperties(String url);

	DataSource createDataSource(Map<String, Object> properties);
}
