package de.thm.mni.vewg30.databaseexporter.ddl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.log4j.Logger;

import de.thm.mni.vewg30.databaseexporter.model.Column;
import de.thm.mni.vewg30.databaseexporter.model.Table;
import de.thm.mni.vewg30.databaseexporter.model.rawtypes.SQLNullType;
import de.thm.mni.vewg30.databaseexporter.model.rawtypes.SQLTypes;

public class DDLColumnModelBuilder {
	private static final Logger log = Logger
			.getLogger(DDLColumnModelBuilder.class);

	private ResultSet rs;
	private Table table;

	public DDLColumnModelBuilder(ResultSet rs, Table table) {
		this.rs = rs;
		this.table = table;
	}

	public Column createColumn() throws SQLException {
		String columnName = getColumnName();
		SQLTypes sqlType = getSQLType();
		if (log.isDebugEnabled()) {
			log.debug(MessageFormat.format(
					"start createColumn for column [{0}] in table [{1}]",
					columnName, table.getTableName()));
		}
		Column result = new Column(table, columnName, sqlType);
		result.setColumnSize(getColumnSize());
		result.setDecimalDigits(getDecimalDigits());
		result.setAutoIncrement(getAutoIncrement());

		result.setNullType(getNullType());

		if (log.isDebugEnabled()) {
			log.debug(MessageFormat.format(
					"stop createColumn with result [{0}]", result));
		}
		return result;
	}

	private boolean getAutoIncrement() throws SQLException {
		String yesNo = rs.getString("IS_AUTOINCREMENT");
		if ("YES".equalsIgnoreCase(yesNo)) {
			return true;
		}
		if ("NO".equalsIgnoreCase(yesNo)) {
			return false;
		}
		return false;
	}

	private SQLNullType getNullType() throws SQLException {
		int value = rs.getInt("NULLABLE");
		return SQLNullType.getSQLTypeByJavaType(value);
	}

	private int getDecimalDigits() throws SQLException {

		int dezimalDigits = rs.getInt("DECIMAL_DIGITS");
		if (rs.wasNull()) {
			return Column.NOTSET_INT_VALUE;
		}
		return dezimalDigits;
	}

	private int getColumnSize() throws SQLException {
		return rs.getInt("COLUMN_SIZE");
	}

	private SQLTypes getSQLType() throws SQLException {
		return SQLTypes.getSQLTypeByJavaType(rs.getInt("DATA_TYPE"));
	}

	private String getColumnName() throws SQLException {
		return rs.getString("COLUMN_NAME");
	}

}
