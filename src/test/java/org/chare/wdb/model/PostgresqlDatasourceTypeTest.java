package org.chare.wdb.model;

import static org.chare.wdb.model.JdbcDatasourceType.DATABASE;
import static org.chare.wdb.model.JdbcDatasourceType.HOSTNAME;
import static org.chare.wdb.model.JdbcDatasourceType.PORT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class PostgresqlDatasourceTypeTest {
	@Test
	public void createUrl() throws Exception {
		PostgresqlDatasourceType type = new PostgresqlDatasourceType();
		Map<String, Object> properties = new HashMap<>();
		properties.put(HOSTNAME, "hostname");
		properties.put(PORT, "1234");
		properties.put(DATABASE, "database");

		String url = type.createUrl(properties);

		assertEquals("jdbc:postgresql://hostname:1234/database?", url);
	}
}