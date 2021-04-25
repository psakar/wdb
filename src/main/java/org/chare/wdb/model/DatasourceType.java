package org.chare.wdb.model;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DatasourceType {
	JDBC(createJdbcPropertyTypes());

	private final List<DatasourcePropertyType<?>> propertyTypes;

	DatasourceType(List<DatasourcePropertyType<?>> propertyTypes) {
		this.propertyTypes = propertyTypes;
	}

	static List<DatasourcePropertyType<?>> createJdbcPropertyTypes() {
		return asList(
			new DatasourcePropertyType<>("type", JdbcDatasourceType.class, true, null),
			new DatasourcePropertyType<>("hostname", String.class, true, "localhost"),
			new DatasourcePropertyType<>("port", Integer.class, true, 0),
			new DatasourcePropertyType<>("databaseName", String.class, true, ""),
			new DatasourcePropertyType<>("username", String.class, true, ""),
			new DatasourcePropertyType<>("password", String.class, false, "")
		);
	}

	public Map<String, Object> createInitialProperties() {
		HashMap<String, Object> map = new HashMap<>();
		for (DatasourcePropertyType propertyType : propertyTypes) {
			map.put(propertyType.getName(), propertyType.getDefaultValue());
		}
		return map;
	}
}
