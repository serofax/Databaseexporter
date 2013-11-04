package de.thm.mni.vewg30.databaseexporter.writer;

import java.io.PrintWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import de.thm.mni.vewg30.databaseexporter.model.Column;
import de.thm.mni.vewg30.databaseexporter.model.Row;
import de.thm.mni.vewg30.databaseexporter.model.Table;

public class DMLTableWriter extends PrintWriter {
	private static final Logger log = Logger.getLogger(DMLTableWriter.class);

	public DMLTableWriter(Writer out) {
		super(out);
	}

	public void writeTable(Table table) {
		if (log.isDebugEnabled()) {
			log.debug(MessageFormat.format("start writeTable for Table [{0}]",
					table.getTableName()));
		}
		if (!table.getRows().isEmpty()) {
			Collection<Column> columns = table.getColumns().values();
			println("INSERT INTO " + table.getTableName() + "("
					+ getColumnNames(columns) + ")");
			println("VALUES");

			Iterator<Row> iterator = table.getRows().iterator();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				String rowString = getRowString(columns, row);
				if (iterator.hasNext()) {
					rowString += ",";
				} else {
					rowString += ";";
				}
				println(rowString);

			}

		} else {
			log.debug("Table is empty");
		}
		log.debug("stop writeTable");
		flush();

	}

	private String getRowString(Collection<Column> columns, Row row) {
		StringBuilder builder = new StringBuilder("(");

		Iterator<Column> iterator = columns.iterator();

		while (iterator.hasNext()) {
			Column column = iterator.next();
			Object object = row.getItems().get(column);
			String formattedString = "NULL";
			if(object != null){
				formattedString = column.getColumnType().getFormattedString(
						row.getItems().get(column));
			}
			builder.append(formattedString);
			if (iterator.hasNext()) {
				builder.append(",");
			}
		}
		builder.append(")");
		return builder.toString();
	}

	private String getColumnNames(Collection<Column> columns) {
		StringBuilder builder = new StringBuilder();

		Iterator<Column> iterator = columns.iterator();
		while (iterator.hasNext()) {
			Column column = iterator.next();
			builder.append(column.getColumnName());
			if (iterator.hasNext()) {
				builder.append(",");
			}

		}
		return builder.toString();
	}
}
