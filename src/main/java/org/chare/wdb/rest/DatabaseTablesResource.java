package org.chare.wdb.rest;

import static org.chare.wdb.rest.Constants.CLIENT_HEADER;
import static org.chare.wdb.rest.Constants.DATASOURCES_PATH;
import static org.chare.wdb.rest.Constants.DATASOURCE_TABLES_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.stream.Collectors;

import org.chare.wdb.model.TableInfo;
import org.chare.wdb.service.DatasourceBrowserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Value;

@RestController
@RequestMapping(path = DATASOURCES_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DatabaseTablesResource {

	private final DatasourceBrowserService browserService;

	@GetMapping(value = "/{name}" + DATASOURCE_TABLES_PATH)
	public List<DatabaseTableDto> browse(@RequestHeader(CLIENT_HEADER) String client, @PathVariable(value = "name") String name ) {
		List<TableInfo> tables = browserService.getTables(client, name);
		return tables.stream()
				.map( table -> new DatabaseTableDto(table.getName()))
				.collect(Collectors.toList());
	}

	@Value
	static class DatabaseTableDto {
		String name;
	}
}
