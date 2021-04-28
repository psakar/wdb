package org.chare.wdb.model;

import static org.chare.wdb.model.JdbcDatasourceType.createJdbcPropertyTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DatasourceType {
	JDBC(createJdbcPropertyTypes());

	private final List<DatasourcePropertyType<?>> propertyTypes;

	DatasourceType(List<DatasourcePropertyType<?>> propertyTypes) {
		this.propertyTypes = propertyTypes;
	}

	public Map<String, Object> createInitialProperties() {
		HashMap<String, Object> map = new HashMap<>();
		for (DatasourcePropertyType propertyType : propertyTypes) {
			map.put(propertyType.getName(), propertyType.getDefaultValue());
		}
		return map;
	}

	public List<DatasourcePropertyType<?>> getPropertyTypes() {
		return propertyTypes;
	}
}
