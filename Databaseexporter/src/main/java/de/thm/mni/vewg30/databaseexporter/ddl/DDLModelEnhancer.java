package de.thm.mni.vewg30.databaseexporter.ddl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import de.thm.mni.vewg30.databaseexporter.model.Column;
import de.thm.mni.vewg30.databaseexporter.model.Database;
import de.thm.mni.vewg30.databaseexporter.model.ForeignKeyReference;
import de.thm.mni.vewg30.databaseexporter.model.Table;

public class DDLModelEnhancer {

	private static final Logger log = Logger.getLogger(DDLModelEnhancer.class);

	private DatabaseMetaData databaseMetaData;

	public DDLModelEnhancer(DatabaseMetaData databaseMetaData) {
		// TODO
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
			Map<Integer,Column> map = new HashMap<Integer, Column>();
			while (rs.next()) {
				
				String columnName = rs.getString("COLUMN_NAME");
				Column column = table.getColumns().get(columnName);
				map.put((int) rs.getShort("KEY_SEQ"),column);
			}
			
			for(int i =1;i<=map.size();++i){
				table.getPrimaryKeys().add(map.get(i));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		log.debug("stop enhanceTableWithPrimaryKeys");

	}

	public Database enhanceWithReferences(Database database)
			throws SQLException {
		log.debug("start enhanceWithReferences");

		for (Table table : database.getTables().values()) {
			if (log.isDebugEnabled()) {
				log.debug(MessageFormat.format(
						"do foreignKeyReference for table [{0}]",
						table.getTableName()));
			}

			ResultSet rs = null;
			Set<ForeignKeyReference> foreignKeySet = new HashSet<ForeignKeyReference>();
			try {
				rs = databaseMetaData.getImportedKeys(null, null,
						table.getTableName());
				while (rs.next()) {
					ForeignKeyReference fkRef = getForeignKeyReference(rs,
							database, foreignKeySet);
					foreignKeySet.add(fkRef);
				}
				for (ForeignKeyReference fkRef : foreignKeySet) {
					fkRef.performDataConssistency();
				}

				// log.debug("----import keys in table " +
				// table.getTableName());
				// while (rs.next()) {
				// log.debug(MessageFormat
				// .format("pktablename[{0}] pkcolumnname[{1}]fktablename[{2}] fkcolumnname[{3}] updaterule[{4}] deleterule[{5}] fkname[{6}] pkname[{7}]",
				// rs.getString("PKTABLE_NAME"),
				// rs.getString("PKCOLUMN_NAME"),
				// rs.getString("FKTABLE_NAME"),
				// rs.getString("FKCOLUMN_NAME"),
				// rs.getString("UPDATE_RULE"),
				// rs.getString("DELETE_RULE"),
				// rs.getString("FK_NAME"),
				// rs.getString("PK_NAME")));
				//
				// }
				// log.debug("----imported keys in table " +
				// table.getTableName());
			} finally {
				if (rs != null) {
					rs.close();
				}
			}

		}
		log.debug("stop enhanceWithReferences");
		return database;
	}

	private ForeignKeyReference getForeignKeyReference(ResultSet rs,
			Database database,
			Set<ForeignKeyReference> alreadyCreatedForeignKeys)
			throws SQLException {
		Table parentTable = database.getTables().get(
				rs.getString("PKTABLE_NAME"));
		Column parentColumn = parentTable.getColumns().get(
				rs.getString("PKCOLUMN_NAME"));

		Table childTable = database.getTables().get(
				rs.getString("FKTABLE_NAME"));
		Column childColumn = childTable.getColumns().get(
				rs.getString("FKCOLUMN_NAME"));

		String referenceName = rs.getString("FK_NAME");
		short numberInComposedFK = rs.getShort("KEY_SEQ");

		ForeignKeyReference foreignKeyReference = null;
		if (numberInComposedFK == 1) {
			foreignKeyReference = new ForeignKeyReference(referenceName,
					parentTable, parentColumn, childTable, childColumn);
		} else {
			foreignKeyReference = getForeignKeyReferenceFromSet(
					alreadyCreatedForeignKeys, parentTable, childTable);
			foreignKeyReference.getChildColumns().add(childColumn);
			foreignKeyReference.getParentColumns().add(parentColumn);
		}

//		System.out.println("Key SEQ" + rs.getShort("KEY_SEQ"));

		// log.debug(MessageFormat
		// .format("pktablename[{0}] pkcolumnname[{1}]fktablename[{2}] fkcolumnname[{3}] updaterule[{4}] deleterule[{5}] fkname[{6}] pkname[{7}]",
		// rs.getString("PKTABLE_NAME"),
		// rs.getString("PKCOLUMN_NAME"),
		// rs.getString("FKTABLE_NAME"),
		// rs.getString("FKCOLUMN_NAME"),
		// rs.getString("UPDATE_RULE"),
		// rs.getString("DELETE_RULE"),
		// rs.getString("FK_NAME"),
		// rs.getString("PK_NAME")));

		return foreignKeyReference;
	}

	private ForeignKeyReference getForeignKeyReferenceFromSet(
			Set<ForeignKeyReference> alreadyCreatedForeignKeys,
			Table parentTable, Table childTable) {
		for (ForeignKeyReference fkRef : alreadyCreatedForeignKeys) {
			if (fkRef.getChildTable().equals(childTable)
					&& fkRef.getParentTable().equals(parentTable)) {
				return fkRef;
			}
		}
		throw new IllegalStateException("Cannot find ForeignKeyReference because it was not created before");
	}

}
