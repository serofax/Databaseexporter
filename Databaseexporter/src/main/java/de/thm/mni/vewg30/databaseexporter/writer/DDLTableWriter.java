package de.thm.mni.vewg30.databaseexporter.writer;

import java.io.PrintWriter;
import java.io.Writer;

import de.thm.mni.vewg30.databaseexporter.model.Column;
import de.thm.mni.vewg30.databaseexporter.model.Table;

public class DDLTableWriter extends PrintWriter {

	public DDLTableWriter(Writer out) {
		super(out);
	}

	public void writeTable(Table table) {
		println("CREATE TABLE " + table.getTableName());
		println("(");
		for (Column column : table.getColumns().values()) {
			println(getColumnString(column));
		}

		String primaryKeyString = getPrimaryKeys(table);
		if (!primaryKeyString.isEmpty()) {
			println(primaryKeyString);
		}

		println(");");
		flush();
	}

	private String getPrimaryKeys(Table table) {
		boolean containsPrimaryKey = false;
		StringBuilder builder = new StringBuilder("\tPRIMARY KEY(");
		for (Column column : table.getPrimaryKeys()) {
			builder.append(column.getColumnName());
			builder.append(",");
			containsPrimaryKey = true;
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(")");
		if (!containsPrimaryKey) {
			return "";
		}

		return builder.toString();
	}

	private String getColumnString(Column column) {
		return String.format("\t%-15s%s%s,", column.getColumnName(), column
				.getColumnType().getSqlLiteral(), getColumnAttributes(column));
	}

	private String getColumnAttributes(Column column) {
		StringBuilder builder = new StringBuilder();
		builder.append(column.getColumnType().getDDLColumnLength(column));
		addColumnAttributes(column, builder);
		return builder.toString();
	}

	private void addColumnAttributes(Column column, StringBuilder builder) {
		builder.append(column.getNullType().getDDLColumnNull(column));
	}
}
