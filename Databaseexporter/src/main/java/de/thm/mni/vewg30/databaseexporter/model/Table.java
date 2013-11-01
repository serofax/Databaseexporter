package de.thm.mni.vewg30.databaseexporter.model;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Table {

	private String tableName;
	private Map<String, Column> columns = new LinkedHashMap<String, Column>();
	private Set<Column> primaryKeys = new HashSet<Column>();

	private Set<ForeignKeyReference> parentTableForeignKeys = new HashSet<ForeignKeyReference>();
	private Set<ForeignKeyReference> childTableForeignKeys = new HashSet<ForeignKeyReference>();

	private Set<Row> rows = new HashSet<Row>();

	public Table(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, Column> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, Column> columns) {
		this.columns = columns;
	}

	public Set<Column> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(Set<Column> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public Set<ForeignKeyReference> getParentTableForeignKeys() {
		return parentTableForeignKeys;
	}

	public void setParentTableForeignKeys(
			Set<ForeignKeyReference> parentTableForeignKeys) {
		this.parentTableForeignKeys = parentTableForeignKeys;
	}

	public Set<ForeignKeyReference> getChildTableForeignKeys() {
		return childTableForeignKeys;
	}

	public void setChildTableForeignKeys(
			Set<ForeignKeyReference> childTableForeignKeys) {
		this.childTableForeignKeys = childTableForeignKeys;
	}

	public Set<Row> getRows() {
		return rows;
	}

	public void setRows(Set<Row> rows) {
		this.rows = rows;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((childTableForeignKeys == null) ? 0 : childTableForeignKeys
						.hashCode());
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime
				* result
				+ ((parentTableForeignKeys == null) ? 0
						: parentTableForeignKeys.hashCode());
		result = prime * result
				+ ((primaryKeys == null) ? 0 : primaryKeys.hashCode());
		result = prime * result + ((rows == null) ? 0 : rows.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Table))
			return false;
		Table other = (Table) obj;
		if (childTableForeignKeys == null) {
			if (other.childTableForeignKeys != null)
				return false;
		} else if (!childTableForeignKeys.equals(other.childTableForeignKeys))
			return false;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (parentTableForeignKeys == null) {
			if (other.parentTableForeignKeys != null)
				return false;
		} else if (!parentTableForeignKeys.equals(other.parentTableForeignKeys))
			return false;
		if (primaryKeys == null) {
			if (other.primaryKeys != null)
				return false;
		} else if (!primaryKeys.equals(other.primaryKeys))
			return false;
		if (rows == null) {
			if (other.rows != null)
				return false;
		} else if (!rows.equals(other.rows))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}

	

}
