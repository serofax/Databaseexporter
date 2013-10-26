package de.thm.mni.vewg30.databaseexporter.model;

import java.util.HashSet;
import java.util.Set;

import de.thm.mni.vewg30.databaseexporter.model.rawtypes.SQLNullType;
import de.thm.mni.vewg30.databaseexporter.model.rawtypes.SQLTypes;

public class Column {
	public static final int NOTSET_INT_VALUE = -1;

	private Table table;
	private String columnName;
	private SQLTypes columnType;
	private int columnSize = NOTSET_INT_VALUE;
	private int decimalDigits = NOTSET_INT_VALUE;

	private SQLNullType nullType = SQLNullType.UNKNOWN;
	private boolean autoIncrement = false;
	private Set<ForeignKeyReference> parentColumnForeignKeys = new HashSet<ForeignKeyReference>();
	private Set<ForeignKeyReference> childColumnForeignKeys = new HashSet<ForeignKeyReference>();
	
//	private boolean isPrimaryKey = false;
//	private Column isReferenceTo = null;

	public Column(Table table, String columnName, SQLTypes columnType) {
		this.table = table;
		this.columnName = columnName;
		this.columnType = columnType;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public SQLTypes getColumnType() {
		return columnType;
	}

	public void setColumnType(SQLTypes columnType) {
		this.columnType = columnType;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public SQLNullType getNullType() {
		return nullType;
	}

	public void setNullType(SQLNullType nullType) {
		this.nullType = nullType;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public Set<ForeignKeyReference> getParentColumnForeignKeys() {
		return parentColumnForeignKeys;
	}

	public void setParentColumnForeignKeys(Set<ForeignKeyReference> parentColumnForeignKeys) {
		this.parentColumnForeignKeys = parentColumnForeignKeys;
	}

	public Set<ForeignKeyReference> getChildColumnForeignKeys() {
		return childColumnForeignKeys;
	}

	public void setChildColumnForeignKeys(Set<ForeignKeyReference> childColumnForeignKeys) {
		this.childColumnForeignKeys = childColumnForeignKeys;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (autoIncrement ? 1231 : 1237);
		result = prime
				* result
				+ ((childColumnForeignKeys == null) ? 0
						: childColumnForeignKeys.hashCode());
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result + columnSize;
		result = prime * result
				+ ((columnType == null) ? 0 : columnType.hashCode());
		result = prime * result + decimalDigits;
		result = prime * result
				+ ((nullType == null) ? 0 : nullType.hashCode());
		result = prime
				* result
				+ ((parentColumnForeignKeys == null) ? 0
						: parentColumnForeignKeys.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Column other = (Column) obj;
		if (autoIncrement != other.autoIncrement)
			return false;
		if (childColumnForeignKeys == null) {
			if (other.childColumnForeignKeys != null)
				return false;
		} else if (!childColumnForeignKeys.equals(other.childColumnForeignKeys))
			return false;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (columnSize != other.columnSize)
			return false;
		if (columnType != other.columnType)
			return false;
		if (decimalDigits != other.decimalDigits)
			return false;
		if (nullType != other.nullType)
			return false;
		if (parentColumnForeignKeys == null) {
			if (other.parentColumnForeignKeys != null)
				return false;
		} else if (!parentColumnForeignKeys
				.equals(other.parentColumnForeignKeys))
			return false;
		return true;
	}

	public boolean isColumnSizeSet() {
		return columnSize != NOTSET_INT_VALUE;
	}

	public boolean isDecimalDigitsSet() {
		return decimalDigits != NOTSET_INT_VALUE;
	}
	
	
	

}
