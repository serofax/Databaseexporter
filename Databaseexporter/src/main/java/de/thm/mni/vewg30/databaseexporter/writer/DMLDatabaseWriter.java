package de.thm.mni.vewg30.databaseexporter.writer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.MessageFormat;

import org.apache.log4j.Logger;

import de.thm.mni.vewg30.databaseexporter.model.Database;
import de.thm.mni.vewg30.databaseexporter.model.Table;

public class DMLDatabaseWriter extends PrintWriter {

	private static final Logger log = Logger.getLogger(DMLDatabaseWriter.class);

	public DMLDatabaseWriter(Writer out) {
		super(out);
	}

	public void writeDatabase(Database database) throws IOException {
		log.debug("start writeDatabase");

		long start = 0L;
		if (log.isInfoEnabled()) {
			start = System.nanoTime();

		}

		@SuppressWarnings("resource")
		DMLTableWriter tableWriter = new DMLTableWriter(out);
		for (Table table : database.getTables().values()) {
			tableWriter.writeTable(table);
			println();
		}
		
		flush();
		
		if (log.isInfoEnabled()) {
			log.info(MessageFormat.format(
					"Took [{0}] ms to write",
					(System.nanoTime() - start) / 1000000.0));

		}

		log.debug("stop writeDatabase");
	}
}
