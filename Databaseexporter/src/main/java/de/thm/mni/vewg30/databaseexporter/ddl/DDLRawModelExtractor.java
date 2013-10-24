package de.thm.mni.vewg30.databaseexporter.ddl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Map;

import org.apache.log4j.Logger;

import de.thm.mni.vewg30.databaseexporter.model.Column;
import de.thm.mni.vewg30.databaseexporter.model.Database;
import de.thm.mni.vewg30.databaseexporter.model.Table;

public class DDLRawModelExtractor {

	private static final Logger log = Logger
			.getLogger(DDLRawModelExtractor.class);

	private DatabaseMetaData metaData;

	public DDLRawModelExtractor(DatabaseMetaData metaData) {
		this.metaData = metaData;
	}

	public Database getDatabase() throws SQLException {
		log.debug("start getDatabase");
		long start = 0L;
		if (log.isInfoEnabled()) {
			start = System.nanoTime();

		}

		String databaseName = metaData.getConnection().getCatalog();
		Database result = new Database(databaseName);
		Map<String, Table> tables = result.getTables();

		ResultSet rs = null;
		try {
			rs = metaData.getTables(null, null, "%", new String[] { "TABLE" });
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				tables.put(tableName, getTable(tableName));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		if (log.isInfoEnabled()) {
			log.info(MessageFormat
					.format("Took [{0}] ms to extract [{1}] tables from database [{2}]",
							(System.nanoTime() - start) / 1000000.0, result
									.getTables().size(), result
									.getDatabaseName()));

		}

		if (log.isDebugEnabled()) {
			log.debug(MessageFormat.format(
					"stop getDatabase with result [{0}]", result));
		}
		return result;
	}

	public Table getTable(String tableName) throws SQLException {
		if (log.isDebugEnabled()) {
			log.debug(MessageFormat.format("start getTable for table [{0}]",
					tableName));
		}

		Table result = new Table(tableName);
		Map<String, Column> columns = result.getColumns();
		ResultSet rs = null;
		try {
			rs = metaData.getColumns(null, null, tableName, "%");
			while (rs.next()) {
				Column column = getColumn(rs, result);
				columns.put(column.getColumnName(), column);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		if (log.isDebugEnabled()) {
			log.debug(MessageFormat.format(
					"stop getTable for table [{0}] with result [{1}] ",
					tableName, result));
		}
		return result;
	}

	private Column getColumn(ResultSet rs, Table table) throws SQLException {
		DDLColumnModelBuilder builder = new DDLColumnModelBuilder(rs, table);
		return builder.createColumn();
	}

}
