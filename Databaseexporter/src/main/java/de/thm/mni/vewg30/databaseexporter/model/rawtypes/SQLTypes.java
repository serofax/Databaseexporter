package de.thm.mni.vewg30.databaseexporter.model.rawtypes;

import java.sql.Types;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import de.thm.mni.vewg30.databaseexporter.model.Column;

/**
 * The most important SQL Types
 * 
 * @author Vincent Wagner
 * 
 */
public enum SQLTypes {
	CHAR(Types.CHAR, "char") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}
	}, //
	DATE(Types.DATE, "date"), //
	DECIMAL(Types.DECIMAL, "decimal") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSetAndDecimalPrecision(column);
		}
	}, // , //
	DOUBLE(Types.DOUBLE, "double") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSetAndDecimalPrecision(column);
		}
	}, //

	FLOAT(Types.FLOAT, "float") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSetAndDecimalPrecision(column);
		}
	}, //

	INTEGER(Types.INTEGER, "integer") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}
	}, //
	NUMERIC(Types.NUMERIC, "numeric"), //
	REAL(Types.REAL, "real") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSetAndDecimalPrecision(column);
		}
	}, //

	TIME(Types.TIME, "time"), //
	TIMESTAMP(Types.TIMESTAMP, "timestamp"), //
	TINYINT(Types.TINYINT, "tinyint") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}
	}, //
	VARCHAR(Types.VARCHAR, "varchar") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}
	} //

	;

	private int javaTypeInt;
	private String sqlLiteral;

	public String getDDLColumnLength(Column column) {
		return "";
	}

	private static String getOnlyColumnLengthIfSet(Column column) {
		if (column.isColumnSizeSet()) {
			return "(" + column.getColumnSize() + ")";
		}
		return "";
	}

	private static String getOnlyColumnLengthIfSetAndDecimalPrecision(
			Column column) {
		String result = "";

		if (column.isColumnSizeSet()) {
			result += "(" + column.getColumnSize();
			if (column.isDecimalDigitsSet() && column.getDecimalDigits() != 0) {
				result += "," + column.getDecimalDigits();
			}

			result += ")";
		}
		return result;
	}

	private static Map<Integer, SQLTypes> types = new HashMap<Integer, SQLTypes>(
			SQLTypes.values().length);

	static {
		for (SQLTypes type : SQLTypes.values()) {
			types.put(type.javaTypeInt, type);
		}
	}

	private SQLTypes(int javaType, String sqlLiteral) {
		this.javaTypeInt = javaType;
		this.sqlLiteral = sqlLiteral;
	}

	public int getJavaType() {
		return javaTypeInt;
	}

	public String getSqlLiteral() {
		return sqlLiteral;
	}

	@Override
	public String toString() {
		return "SQLTypes[name=" + name() + ",sqlLiteral=" + sqlLiteral + "]";
	}

	public static SQLTypes getSQLTypeByJavaType(int javaType) {
		SQLTypes type = types.get(javaType);
		if (type != null) {
			return type;
		}
		throw new IllegalArgumentException("The javaSQLType[" + javaType
				+ "] is unknown");
	}

}
