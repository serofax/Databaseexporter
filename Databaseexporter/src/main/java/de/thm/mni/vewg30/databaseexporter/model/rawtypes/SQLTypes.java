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

		@Override
		public String getFormattedString(Object object) {
			return withApostrophe((String) object);
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
			return ((Integer) object).toString();
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

	TIME(Types.TIME, "time") {
		private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
				
		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(formatter.format((Date)object));
		}
	}, //
	TIMESTAMP(Types.TIMESTAMP, "timestamp") {
		private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		@Override
		public String getFormattedString(Object object) {
			return withApostrophe(formatter.format((Date)object));
		}
	}, //
	TINYINT(Types.TINYINT, "tinyint") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return ((Integer)object).toString();
			
		}
	}, //
	VARCHAR(Types.VARCHAR, "varchar") {
		@Override
		public String getDDLColumnLength(Column column) {
			return getOnlyColumnLengthIfSet(column);
		}

		@Override
		public String getFormattedString(Object object) {
			return withApostrophe((String)object);
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
	
	private static String withApostrophe(String s){
		return "'" + s + "'";
	}

	public String getDDLColumnLength(Column column) {
		return "";
	}

	public abstract String getFormattedString(Object object);

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
