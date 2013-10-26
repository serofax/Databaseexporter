package de.thm.mni.vewg30.databaseexporter.model;

public class ForeignKeyReference {
	private String name;

	private Table parentTable;
	private Column parentColumn;

	private Table childTable;
	private Column childColumn;

	public ForeignKeyReference(Table parentTable, Column parentColumn, Table childTable,
			Column childColumn) {
		this("FK" + System.nanoTime(), parentTable, parentColumn, childTable,
				childColumn);
	}

	public ForeignKeyReference(String name, Table parentTable, Column parentColumn,
			Table childTable, Column childColumn) {
		super();
		this.name = name;
		this.parentTable = parentTable;
		this.parentColumn = parentColumn;
		this.childTable = childTable;
		this.childColumn = childColumn;
	}

	public void performDataConssistency() {
		parentTable.getParentTableForeignKeys().add(this);
		parentColumn.getParentColumnForeignKeys().add(this);

		childTable.getChildTableForeignKeys().add(this);
		childColumn.getChildColumnForeignKeys().add(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Table getParentTable() {
		return parentTable;
	}

	public void setParentTable(Table parentTable) {
		this.parentTable = parentTable;
	}

	public Column getParentColumn() {
		return parentColumn;
	}

	public void setParentColumn(Column parentColumn) {
		this.parentColumn = parentColumn;
	}

	public Table getChildTable() {
		return childTable;
	}

	public void setChildTable(Table childTable) {
		this.childTable = childTable;
	}

	public Column getChildColumn() {
		return childColumn;
	}

	public void setChildColumn(Column childColumn) {
		this.childColumn = childColumn;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ForeignKeyReference))
			return false;
		ForeignKeyReference other = (ForeignKeyReference) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
