package org.chare.wdb.service;

import static org.chare.wdb.model.DatasourceType.JDBC;

import org.chare.wdb.model.DatasourceType;
import org.chare.wdb.repository.DatasourceMetadataReader;
import org.chare.wdb.repository.JdbcDatasourceTypeResolver;
import org.chare.wdb.repository.JdbcTableInfoReader;
import org.springframework.stereotype.Component;

@Component
public class DatasourceMetadataReaderResolver {
	public DatasourceMetadataReader getMetadataReader(DatasourceType datasourceType) {
		if (JDBC.equals(datasourceType)) {
			return new DatasourceMetadataReader.JdbcDatasourceMetadataReader(new JdbcDatasourceTypeResolver(), new JdbcTableInfoReader());
		}
		throw new IllegalArgumentException("Unknown datasource type");
	}

}
