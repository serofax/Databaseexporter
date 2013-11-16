package de.thm.mni.vewg30.databaseexporter.model.rawtypes;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.thm.mni.vewg30.databaseexporter.model.Column;
import de.thm.mni.vewg30.databaseexporter.util.FormatterUtil;

/**
 * The most important SQL Types
 * 
 * @author Vincent Wagner
 * 
 */
public enum SQLTypes {
	ARRAY(Types.CHAR, "array") {
		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(object.toString());
		}
	}, //
	BIGINT(Types.BIGINT, "bigint") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return object.toString();
		}
	}, //
	BINARY(Types.BINARY, "binary") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return byteArrayToString((byte[]) object);
		}
	}, //
	BIT(Types.BIT, "bit") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			if(object instanceof byte[]){
				return byteArrayToString((byte[]) object);
			}else{
				if(object instanceof Boolean){
					return ((Boolean)object)?"x'1'":"x'0'";
				}
				throw new IllegalArgumentException("Cannot format " + object +" for type " + name());
			}
		}
	}, //
	BLOB(Types.BLOB, "blob") {
		@Override
		public String getFormattedString(Object object) {
			return byteArrayToString((byte[])object);
		}
	}, //
	BOOLEAN(Types.BOOLEAN, "boolean") {
		@Override
		public String getFormattedString(Object object) {
			return object.toString();
		}
	}, //

	CHAR(Types.CHAR, "char") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(object.toString());
		}
	}, //
	CLOB(Types.CLOB, "clob") {
		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(object.toString());
		}
	}, //
	DATALINK(Types.DATALINK, "datalink") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(object.toString());
		}
	}, //
	DATE(Types.DATE, "date") {
		private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		@Override
		public String getFormattedString(Object object) {

			return withApostrophe(formatter.format((Date) object));
		}
	}, //
	DECIMAL(Types.DECIMAL, "decimal") {
		private DecimalFormat formatter = (DecimalFormat) NumberFormat
				.getNumberInstance(Locale.US);

		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSetAndDecimalPrecision(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return formatter.format((BigDecimal) object);
		}
	}, //
	DISTINCT(Types.DISTINCT, "distinct") {

		@Override
		public String getFormattedString(Object object) {
			return object.toString();
		}
	}, //
	DOUBLE(Types.DOUBLE, "double") {
		private DecimalFormat formatter = (DecimalFormat) NumberFormat
				.getNumberInstance(Locale.US);

		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSetAndDecimalPrecision(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return formatter.format((BigDecimal) object);
		}
	}, //

	FLOAT(Types.FLOAT, "float") {
		private DecimalFormat formatter = (DecimalFormat) NumberFormat
				.getNumberInstance(Locale.US);

		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSetAndDecimalPrecision(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return formatter.format((BigDecimal) object);
		}
	}, //

	INTEGER(Types.INTEGER, "integer") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return object.toString();
		}
	}, //
	LONGNVARCHAR(Types.LONGNVARCHAR, "longnvarchar") {
		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(object.toString());
		}
	}, //
	LONGVARBINARY(Types.LONGVARBINARY, "longvarbinary") {
		@Override
		public String getFormattedString(Object object) {
			return byteArrayToString((byte[])object);
		}
	}, //
	LONGVARCHAR(Types.LONGVARCHAR, "longvarchar") {
		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(object.toString());
		}
	}, //
	NCHAR(Types.NCHAR, "nchar") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(object.toString());
		}
	}, //
	NCLOB(Types.NCLOB, "nclob") {
		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(object.toString());
		}
	}, //
	NULL(Types.NULL, "null") {
		@Override
		public String getFormattedString(Object object) {
			return object.toString();
		}
	}, //
	NUMERIC(Types.NUMERIC, "numeric") {
		private DecimalFormat formatter = (DecimalFormat) NumberFormat
				.getNumberInstance(Locale.US);

		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSetAndDecimalPrecision(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return formatter.format((BigDecimal) object);
		}
	}, //
	NVARCHAR(Types.NVARCHAR, "nvarchar") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(object.toString());
		}
	}, //
	REAL(Types.REAL, "real") {
		private DecimalFormat formatter = (DecimalFormat) NumberFormat
				.getNumberInstance(Locale.US);

		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSetAndDecimalPrecision(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return formatter.format((Double) object);
		}

	}, //
		// OTHER ...
	SMALLINT(Types.SMALLINT, "smallint") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return object.toString();
		}
	}, //

	TIME(Types.TIME, "time") {
		private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(formatter.format((Date) object));
		}
	}, //
	TIMESTAMP(Types.TIMESTAMP, "timestamp") {
		private SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(formatter.format((Date) object));
		}
	}, //
	TINYINT(Types.TINYINT, "tinyint") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return object.toString();

		}
	}, //
	VARCHAR(Types.VARCHAR, "varchar") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(object.toString());
		}
	} //
	;

	private int javaTypeInt;
	private String sqlLiteral;

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

	public String getDDLColumnLength(Column column) {
		return "";
	}

	/**
	 * Format the object for the specified type.
	 * <b>DOES NOT TAKE NULL!!!</b>
	 * @param object
	 * @return
	 */
	public abstract String getFormattedString(Object object);

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
	
	//Helpermethods

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

	private static String byteArrayToString(byte[] array){
		return "x'" + FormatterUtil.bytesToHex(array) + "'";
	}

	private static String withApostrophe(String s) {
		return "'" + s.replace("'", "''") + "'";
	}
}
