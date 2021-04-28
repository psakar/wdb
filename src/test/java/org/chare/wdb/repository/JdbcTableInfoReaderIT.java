package org.chare.wdb.repository;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.chare.wdb.model.TableInfo;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;


class JdbcTableInfoReaderIT {

	static String POSTGRES_TEST_IMAGE = System.getProperty("POSTGRES_TEST_IMAGE", "postgres:11.1");

	@ClassRule
	public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer(POSTGRES_TEST_IMAGE)
			.withDatabaseName("demo")
			.withUsername("postgres")
			.withPassword("start123")
			.withInitScript("create-db.sql")
			;

	@BeforeAll
	public static void beforeAll() {
		postgreSQLContainer.start();
	}

	@AfterAll
	public static void afterAll() {
		postgreSQLContainer.stop();
	}


	@Test
	public void testReadTableInfos() throws SQLException {
		Connection con = postgreSQLContainer.createConnection("");

		List<TableInfo> infos = new JdbcTableInfoReader().readTableInfos(con);

		assertArrayEquals(asList(new TableInfo("table1")).toArray(), infos.toArray());
	}

}