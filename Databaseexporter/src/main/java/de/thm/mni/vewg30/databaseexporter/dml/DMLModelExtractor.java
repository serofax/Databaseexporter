package de.thm.mni.vewg30.databaseexporter.dml;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

import org.apache.log4j.Logger;

import de.thm.mni.vewg30.databaseexporter.model.Column;
import de.thm.mni.vewg30.databaseexporter.model.Database;
import de.thm.mni.vewg30.databaseexporter.model.Row;
import de.thm.mni.vewg30.databaseexporter.model.Table;

public class DMLModelExtractor {
	
	private static final Logger log = Logger
			.getLogger(DMLModelExtractor.class);
	private Connection connection;
	
	
	public DMLModelExtractor(Connection connection) {
		this.connection = connection;
	}
	
	public Database getDatabaseWithData(Database database) throws SQLException{
		log.debug("start getDatabaseWithData");
		
		
		for(Table table: database.getTables().values()){
			fillTableWithData(table);
		}
		log.debug("stop getDatabaseWithData");
		return database;
	}

	private void fillTableWithData(Table table) throws SQLException {
		if(log.isDebugEnabled()){
			log.debug("start fillTableWithData for table " + table.getTableName());
		}
		
		
		Set<Row> rows = table.getRows();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = connection.prepareStatement("SELECT * FROM " + table.getTableName());
			System.out.println(stmt.toString());
			rs = stmt.executeQuery();
			
			while(rs.next()){
				rows.add(createRow(rs, table.getColumns().values()));
			}
			
			
		}finally{
			if(rs != null){
				rs.close();
			}
			if(stmt != null){
				stmt.close();
			}
		}
		
		log.debug("stop fillTableWithData");
		
		
	}

	private Row createRow(ResultSet rs, Collection<Column> columns) throws SQLException {
		Row row = new Row();
		for(Column column: columns){
			row.getItems().put(column, rs.getObject(column.getColumnName()));			
		}
		return row;
	}

}
