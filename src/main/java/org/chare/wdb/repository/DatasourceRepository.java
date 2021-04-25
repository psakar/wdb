package org.chare.wdb.repository;

import org.chare.wdb.entity.ClientDatasources;
import org.springframework.data.repository.CrudRepository;

public interface DatasourceRepository extends CrudRepository<ClientDatasources, String> {
//	List<Datasource> findAll(String client);
//	void add(String client, Datasource datasource);
//	void update(String client, Datasource datasource);
//	void delete(String client, String name);
}
