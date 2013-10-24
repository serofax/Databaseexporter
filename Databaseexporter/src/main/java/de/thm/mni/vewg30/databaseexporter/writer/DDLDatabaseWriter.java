package de.thm.mni.vewg30.databaseexporter.writer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.MessageFormat;

import org.apache.log4j.Logger;

import de.thm.mni.vewg30.databaseexporter.model.Database;
import de.thm.mni.vewg30.databaseexporter.model.Table;

public class DDLDatabaseWriter extends PrintWriter {

	private static final Logger log = Logger.getLogger(DDLDatabaseWriter.class);

	public DDLDatabaseWriter(Writer out) {
		super(out);
	}

	public void writeDatabase(Database database) throws IOException {
		log.debug("start writeDatabase");

		long start = 0L;
		if (log.isInfoEnabled()) {
			start = System.nanoTime();

		}
		writeDropTable(database);

		println();
		println();

		println("/*Create new tables*/");

		@SuppressWarnings("resource")
		DDLTableWriter tablewriter = new DDLTableWriter(out);
		for (Table table : database.getTables().values()) {
			tablewriter.writeTable(table);
		}
		if (log.isInfoEnabled()) {
			log.info(MessageFormat.format(
					"Took [{0}] ms to write [{1}] tables from database [{2}]",
					(System.nanoTime() - start) / 1000000.0, database
							.getTables().size(), database.getDatabaseName()));

		}

		log.debug("stop writeDatabase");
	}

	private void writeDropTable(Database database) throws IOException {
		println("/* Delete tables */");

		for (Table table : database.getTables().values()) {
			println(getDropTableString(table));
		}

	}

	private String getDropTableString(Table table) {
		return "DROP TABLE " + table.getTableName() + " CASCADE;";
	}

}
