package de.thm.mni.vewg30.databaseexporter.model;

public class Column {
	public static final int NOTSET_INT_VALUE = -1;

	private Table table;
	private String columnName;
	private SQLTypes columnType;
	private int columnSize = NOTSET_INT_VALUE;
	private int decimalDigits = NOTSET_INT_VALUE;

	private SQLNullType nullType = SQLNullType.UNKNOWN;
	private boolean autoIncrement = false;
	private boolean isPrimaryKey = false;
	private Column isReferenceTo = null;

	public Column(Table table, String columnName, SQLTypes columnType) {
		this.table = table;
		this.columnName = columnName;
		this.columnType = columnType;
	}

	public boolean isColumnSizeSet() {
		return columnSize != NOTSET_INT_VALUE;
	}

	public boolean isDecimalDigitsSet() {
		return decimalDigits != NOTSET_INT_VALUE;
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

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
		if (this.isPrimaryKey) {
			this.nullType = SQLNullType.NOT_NULL;
		}
	}

	public Column getIsReferenceTo() {
		return isReferenceTo;
	}

	public void setIsReferenceTo(Column isReferenceTo) {
		this.isReferenceTo = isReferenceTo;
	}

	public Table getTable() {
		return table;
	}

	public String getColumnName() {
		return columnName;
	}

	public SQLTypes getColumnType() {
		return columnType;
	}

	public SQLNullType getNullType() {
		return nullType;
	}

	public void setNullType(SQLNullType nullType) {
		this.nullType = nullType;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setColumnType(SQLTypes columnType) {
		this.columnType = columnType;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (autoIncrement ? 1231 : 1237);
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result + columnSize;
		result = prime * result
				+ ((columnType == null) ? 0 : columnType.hashCode());
		result = prime * result + decimalDigits;
		result = prime * result + (isPrimaryKey ? 1231 : 1237);
		result = prime * result
				+ ((isReferenceTo == null) ? 0 : isReferenceTo.hashCode());
		result = prime * result
				+ ((nullType == null) ? 0 : nullType.hashCode());
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
		if (isPrimaryKey != other.isPrimaryKey)
			return false;
		if (isReferenceTo == null) {
			if (other.isReferenceTo != null)
				return false;
		} else if (!isReferenceTo.equals(other.isReferenceTo))
			return false;
		if (nullType != other.nullType)
			return false;
		return true;
	}

}
