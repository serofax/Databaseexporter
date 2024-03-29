package de.thm.mni.vewg30.databaseexporter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


import de.thm.mni.vewg30.databaseexporter.ddl.DDLModelEnhancer;
import de.thm.mni.vewg30.databaseexporter.ddl.DDLRawModelExtractor;
import de.thm.mni.vewg30.databaseexporter.dml.DMLModelExtractor;
import de.thm.mni.vewg30.databaseexporter.model.Database;
import de.thm.mni.vewg30.databaseexporter.writer.DDLDatabaseWriter;
import de.thm.mni.vewg30.databaseexporter.writer.DMLDatabaseWriter;

public class Test {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String urlPortDatabase = "jdbc:mysql://localhost/azamon";
	private static String user = "root";
	private static String password = "7895278952";

	public static void main(String[] args) throws ClassNotFoundException,
			IOException {
		Class.forName(driver);
		System.out.println("loaded driver " + driver);

		Connection con = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(urlPortDatabase, user, password);
			System.out.println("connection established");

			DatabaseMetaData metaData = con.getMetaData();

			// ResultSet crossReference = metaData.getCrossReference(null, null,
			// "Orderitems", null, null, "Orders");

			// while(crossReference.next()){
			// System.out.println("moep");
			// System.out.println(crossReference.getString("FKCOLUMN_NAME"));
			// }

			DDLRawModelExtractor extractor = new DDLRawModelExtractor(metaData);
			DDLModelEnhancer enhancer = new DDLModelEnhancer(metaData);
			Database database = extractor.getDatabase();
			enhancer.enhanceDatabase(database);
			DMLModelExtractor dmlExtractor = new DMLModelExtractor(con);
			database = dmlExtractor.getDatabaseWithData(database);

			DDLDatabaseWriter writer = new DDLDatabaseWriter(
					new OutputStreamWriter(System.out));
			writer.writeDatabase(database);
			writer.flush();
			DMLDatabaseWriter dmlWriter = new DMLDatabaseWriter(
					new OutputStreamWriter(System.out));
			dmlWriter.writeDatabase(database);
			dmlWriter.flush();

			System.out.println("finished");

			// for (Table table : database.getTables().values()) {
			// for (Column column : table.getColumns().values()) {
			// System.out.println(column);
			// }
			// }

			// System.out.println(database);
			// System.out.println(metaData.getConnection().getCatalog());
			//
			// rs = metaData.getTables(null, null, "%", new String[] { "TABLE"
			// });
			// while (rs.next()) {
			// System.out.println(rs.getString("TABLE_NAME"));
			//
			// }

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (rs != null) {
					rs.close();
				}

				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
