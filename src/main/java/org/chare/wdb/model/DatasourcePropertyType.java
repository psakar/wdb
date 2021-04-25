package org.chare.wdb.model;

import lombok.Value;

@Value
public class DatasourcePropertyType<T> {
	String name;
	Class<T> type;
	boolean mandatory;
	T defaultValue;
}
