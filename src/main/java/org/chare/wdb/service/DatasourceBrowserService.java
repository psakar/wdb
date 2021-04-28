package org.chare.wdb.service;

import java.util.List;

import org.chare.wdb.entity.ClientDatasources;
import org.chare.wdb.model.Datasource;
import org.chare.wdb.model.TableInfo;
import org.chare.wdb.repository.DatasourceMetadataReader;
import org.chare.wdb.repository.DatasourceRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

public interface DatasourceBrowserService {
	List<TableInfo> getTables(String client, String datasourceName);

	@Service
	@AllArgsConstructor
	class DatasourceBrowserServiceImpl implements DatasourceBrowserService {
		private final DatasourceRepository datasourceRepository;
		private final DatasourceMetadataReaderResolver datasourceMetadataReaderResolver;

		@Override
		public List<TableInfo> getTables(String client, String datasourceName) {
			ClientDatasources clientDatasources = datasourceRepository.findById(client).orElse(null);
			if (clientDatasources == null) {
				throw new IllegalArgumentException("Client not found");
			}
			Datasource datasource = clientDatasources.getDatasources().get(datasourceName);
			if (datasource == null) {
				throw new IllegalArgumentException("Datasource not found");
			}
			DatasourceMetadataReader metadataReader = datasourceMetadataReaderResolver.getMetadataReader(datasource.getType());
			return metadataReader.readTableInfos(datasource);
		}

	}

}
