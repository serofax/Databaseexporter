package de.thm.mni.vewg30.databaseexporter.model;

import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Map;

public enum SQLNullType {
	NOT_NULL(DatabaseMetaData.columnNoNulls) {
		@Override
		public String getDDLColumnNull(Column column) {
			return " NOT NULL";
		}
	}, //
	NULLABLE(DatabaseMetaData.columnNullable) {
		@Override
		public String getDDLColumnNull(Column column) {
			return " NULL";
		}
	}, //
	UNKNOWN(DatabaseMetaData.columnNullableUnknown)//
	;

	private int javaNullValue;

	public String getDDLColumnNull(Column column) {
		return "";
	}

	private static Map<Integer, SQLNullType> types = new HashMap<Integer, SQLNullType>(
			SQLNullType.values().length);

	static {
		for (SQLNullType type : SQLNullType.values()) {
			types.put(type.javaNullValue, type);
		}
	}

	private SQLNullType(int javaNullValue) {
		this.javaNullValue = javaNullValue;

	}

	public int getJavaNullValue() {
		return javaNullValue;
	}

	public static SQLNullType getSQLTypeByJavaType(int javaNullValue) {
		SQLNullType type = types.get(javaNullValue);
		if (type != null) {
			return type;
		}
		throw new IllegalArgumentException("The SQLNullValue[" + javaNullValue
				+ "] is unknown");
	}

}
