package org.chare.wdb.rest;

import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.chare.wdb.model.DatasourceType.JDBC;
import static org.chare.wdb.rest.Constants.CLIENT_HEADER;
import static org.chare.wdb.rest.Constants.DATASOURCES_PATH;
import static org.mockito.Mockito.when;

import org.chare.wdb.entity.ClientDatasources;
import org.chare.wdb.model.Datasource;
import org.chare.wdb.repository.DatasourceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DatasourcesResource.class)
@ContextConfiguration(classes = { DatasourcesResourceIT.ContextConfig.class })
class DatasourcesResourceIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DatasourceRepository datasourceRepository;

	@Test
	void get() throws Exception {
		String clientName = "client" + currentTimeMillis();
		String datasourceName = "datasource" + currentTimeMillis();
		Datasource datasource = new Datasource(datasourceName, JDBC);
		ClientDatasources clientDatasources = new ClientDatasources(clientName, asList(datasource));

		when(datasourceRepository.findById(clientName)).thenReturn(of(clientDatasources));

		mockMvc.perform(
				MockMvcRequestBuilders.get(DATASOURCES_PATH + "/" + datasourceName)
							.contentType(MediaType.APPLICATION_JSON)
							.header(CLIENT_HEADER, clientName)
						)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(createDatasourceJson(datasourceName)));
	}

	static String createDatasourceJson(String name) {
		return "{\"name\":\"" + name + "\",\"type\":\"JDBC\",\"properties\":{\"hostname\":\"localhost\",\"password\":\"\",\"databaseName\":\"\",\"port\":0,\"type\":null,\"username\":\"\"}}";
	}

	@Configuration
	@EnableWebMvc
	static class ContextConfig {

		@MockBean
		private DatasourceRepository datasourceRepository;

		@Bean
		DatasourcesResource datasourcesResource() {
			return new DatasourcesResource(datasourceRepository);
		}

	}
}