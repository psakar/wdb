package org.chare.wdb.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Datasource {
	String name;
	DatasourceType type;
	Map<String, Object> properties;

	public Datasource(String name, DatasourceType type) {
		this(name, type, type.createInitialProperties());
	}
}
