package org.chare.wdb.repository;

import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.asList;
import static org.chare.wdb.model.DatasourceType.JDBC;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

import org.chare.wdb.entity.ClientDatasources;
import org.chare.wdb.model.Datasource;
import org.chare.wdb.model.PostgresqlDatasourceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataRedisTest
@ContextConfiguration(classes = {RedisConfig.class, DatasourceRedisRepositoryIT.ContextConfig.class})
@DirtiesContext(classMode = AFTER_CLASS)
class DatasourceRedisRepositoryIT {


	@Autowired
	private DatasourceRepository datasourceRepository;

	@Test
	void findAll() {
		datasourceRepository.findAll();
	}

	@Test
	void add() {
		String name = "datasourceName " + currentTimeMillis();
		Datasource datasource = new Datasource(name, JDBC);
		datasource.getProperties().put("type", PostgresqlDatasourceType.class.getName());
		String clientName = "clientName" + currentTimeMillis();
		ClientDatasources clientDatasources = new ClientDatasources(clientName, asList(datasource));

		datasourceRepository.save(clientDatasources);

		ClientDatasources persisted = datasourceRepository.findById(clientName).get();
		assertEquals(clientDatasources, persisted);
	}

	@Configuration
	@EnableRedisRepositories("org.chare.wdb.repository")
	@EntityScan("com.chare.wdb.entity")
	static class ContextConfig {
	}


}