package org.chare.wdb.repository;

import static org.chare.wdb.model.DatasourceType.JDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.chare.wdb.model.Datasource;
import org.chare.wdb.model.DatasourceType;
import org.chare.wdb.model.JdbcDatasourceType;
import org.chare.wdb.model.TableInfo;
import org.chare.wdb.service.DatasourceBrowserService;

import lombok.AllArgsConstructor;

public interface DatasourceMetadataReader {

	public List<TableInfo> readTableInfos (Datasource datasource);

	@AllArgsConstructor
	static class JdbcDatasourceMetadataReader implements DatasourceMetadataReader {
		private final JdbcDatasourceTypeResolver jdbcDatasourceTypeResolver;
		private final JdbcTableInfoReader tableInfoReader;

		public List<TableInfo> readTableInfos (Datasource datasource) {
			try {
				JdbcDatasourceType type = jdbcDatasourceTypeResolver.getType((String)datasource.getProperties().get("type"));
				try (Connection connection = type.getConnection(datasource.getProperties())) {
					return tableInfoReader.readTableInfos(connection);
				}
			} catch (SQLException e) {
				throw new IllegalArgumentException("Can not read table info " + e.getMessage(), e);
			}
		}
	}

}
