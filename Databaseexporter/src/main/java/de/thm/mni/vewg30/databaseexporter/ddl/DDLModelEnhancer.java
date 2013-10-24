package de.thm.mni.vewg30.databaseexporter.ddl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.log4j.Logger;

import de.thm.mni.vewg30.databaseexporter.model.Column;
import de.thm.mni.vewg30.databaseexporter.model.Database;
import de.thm.mni.vewg30.databaseexporter.model.Table;

public class DDLModelEnhancer {

	private static final Logger log = Logger.getLogger(DDLModelEnhancer.class);

	private DatabaseMetaData databaseMetaData;

	public DDLModelEnhancer(DatabaseMetaData databaseMetaData) {
		//TODO
		this.databaseMetaData = databaseMetaData;
	}

	public Database enhanceDatabase(Database database) throws SQLException {
		log.debug("start enhanceDatabase");
		enhanceWithPrimaryKeys(database);
		enhanceWithReferences(database);
		log.debug("stop enhanceDatabase");
		return database;
	}

	public Database enhanceWithPrimaryKeys(Database database)
			throws SQLException {
		log.debug("start enhanceWithPrimaryKeys");
		for (String tableName : database.getTables().keySet()) {
			Table table = database.getTables().get(tableName);
			enhanceTableWithPrimaryKeys(table);
		}
		log.debug("stop enhanceWithPrimaryKeys");
		return database;
	}

	private void enhanceTableWithPrimaryKeys(Table table) throws SQLException {
		if (log.isDebugEnabled()) {
			log.debug(MessageFormat.format(
					"start enhanceTableWithPrimaryKeys for table [{0}]",
					table.getTableName()));
		}

		ResultSet rs = null;
		try {
			rs = databaseMetaData.getPrimaryKeys(null, null,
					table.getTableName());
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				Column column = table.getColumns().get(columnName);
				column.setPrimaryKey(true);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		log.debug("stop enhanceTableWithPrimaryKeys");

	}

	public Database enhanceWithReferences(Database database) {
		return database;
	}

}
