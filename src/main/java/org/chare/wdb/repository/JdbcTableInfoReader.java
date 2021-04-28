package org.chare.wdb.repository;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.chare.wdb.model.TableInfo;

public class JdbcTableInfoReader {
	public List<TableInfo> readTableInfos(Connection conn) throws SQLException {
		DatabaseMetaData md = conn.getMetaData();
		ResultSet rs = md.getTables(null, null, "%", null);
		List<TableInfo> list = new ArrayList<>();
		while (rs.next()) {
			list.add(new TableInfo(rs.getString("TABLE_NAME")));
		}
		return list;
	}
}
