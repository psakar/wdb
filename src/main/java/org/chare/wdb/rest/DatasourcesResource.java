package org.chare.wdb.rest;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS;
import static org.chare.wdb.rest.Constants.CLIENT_HEADER;
import static org.chare.wdb.rest.Constants.DATASOURCES_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.Map;

import org.chare.wdb.entity.ClientDatasources;
import org.chare.wdb.model.Datasource;
import org.chare.wdb.model.DatasourcePropertyType;
import org.chare.wdb.model.DatasourceType;
import org.chare.wdb.repository.DatasourceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(path = DATASOURCES_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DatasourcesResource {

	private final DatasourceRepository datasourceRepository;

	@GetMapping(value = "/{name}")
	public DatasourceDto get(@RequestHeader(CLIENT_HEADER) String client, @PathVariable(value = "name") String name ) {
		ClientDatasources clientDatasources = datasourceRepository.findById(client).orElse(null);
		if (clientDatasources == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
		}
		Datasource datasource = clientDatasources.getDatasources().get(name);
		if (datasource == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Datasource not found");
		}
		return new DatasourceDto(datasource);
	}

	@PostMapping(value = "/")
	public void add(@RequestHeader(CLIENT_HEADER) String client, @RequestBody DatasourceDto datasourceDto) {
		ClientDatasources datasources = datasourceRepository.findById(client).orElse(new ClientDatasources(client));
		if (datasourceDto == null || isEmpty(datasourceDto.name)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datasource name missing");
		}
		Datasource datasource = convertToDatasource(datasourceDto);
		datasources.getDatasources().put(datasource.getName(), datasource);
		datasourceRepository.save(datasources);
	}

	@DeleteMapping(value = "/{name}")
	public void delete(@RequestHeader(CLIENT_HEADER) String client, @PathVariable(value = "name") String name ) {
		ClientDatasources clientDatasources = datasourceRepository.findById(client).orElse(null);
		if (clientDatasources == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
		}
		Datasource datasource = clientDatasources.getDatasources().get(name);
		if (datasource == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Datasource not found");
		}
		clientDatasources.getDatasources().remove(name);
		datasourceRepository.save(clientDatasources);
	}

	static Datasource convertToDatasource(DatasourceDto datasourceDto) {
		String name = datasourceDto.name;
		DatasourceType type = DatasourceType.JDBC;
		Datasource datasource = new Datasource(name, type);
		for (DatasourcePropertyType<?> propertyType : datasource.getType().getPropertyTypes()) {
			String typeName = propertyType.getName();
			datasource.getProperties().put(typeName, datasourceDto.properties.get(typeName)); //FIXME map to correct type
		}
		return datasource;
	}

	@PutMapping(value = "/")
	public void update(@RequestHeader(CLIENT_HEADER) String client, @RequestBody DatasourceDto datasourceDto) {
		ClientDatasources datasources = datasourceRepository.findById(client).orElse(new ClientDatasources(client));
		if (datasourceDto == null || isEmpty(datasourceDto.name)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datasource name missing");
		}
		Datasource datasource = convertToDatasource(datasourceDto);
		datasources.getDatasources().put(datasource.getName(), datasource);
		datasourceRepository.save(datasources);
	}

	@Data
	@NoArgsConstructor
	static class DatasourceDto {
		String name;
		String type;
		@JsonInclude(ALWAYS)
		Map<String, Object> properties;

		DatasourceDto(Datasource datasource) {
			this.name = datasource.getName();
			this.type = datasource.getType().name();
			this.properties = datasource.getProperties();
		}

	}
}
