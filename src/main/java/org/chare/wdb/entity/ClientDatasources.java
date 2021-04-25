package org.chare.wdb.entity;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.chare.wdb.model.Datasource;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@RedisHash("ClientDatasources")
@NoArgsConstructor
@AllArgsConstructor
public class ClientDatasources {
	@Id
	private String name;
	private Map<String, Datasource> datasources;

	public ClientDatasources(String name, List<Datasource> datasources) {
		this(name, toMap(datasources));
	}

	static Map<String, Datasource> toMap(List<Datasource> list) {
		return list.stream().collect(Collectors.toMap(Datasource::getName, Function.identity()));
	}
}
