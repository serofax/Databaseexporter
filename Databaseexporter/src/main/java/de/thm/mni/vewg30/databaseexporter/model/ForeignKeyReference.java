package de.thm.mni.vewg30.databaseexporter.model;

import java.util.ArrayList;
import java.util.List;

public class ForeignKeyReference {
	private String name;

	// ALTER TABLE ChildTable
	// ADD FOREIGN KEY (FK1,FK2) REFERENCES ParentTable(PK1,PK2)

	private Table parentTable;
	private List<Column> parentColumns = new ArrayList<Column>();

	private Table childTable;
	private List<Column> childColumns = new ArrayList<Column>();

	public ForeignKeyReference(Table parentTable, Column parentColumn,
			Table childTable, Column childColumn) {
		this("FK" + System.nanoTime(), parentTable, parentColumn, childTable,
				childColumn);
	}

	public ForeignKeyReference(String name, Table parentTable,
			Column parentColumn, Table childTable,
			Column childColumn) {
		super();
		this.name = name;
		this.parentTable = parentTable;
		this.parentColumns.add(parentColumn);
		this.childTable = childTable;
		this.childColumns.add(childColumn);
	}

	public void performDataConssistency() {
		parentTable.getParentTableForeignKeys().add(this);
		for (Column column : parentColumns) {
			column.getParentColumnForeignKeys().add(this);
		}
		// parentColumns.getParentColumnForeignKeys().add(this);

		childTable.getChildTableForeignKeys().add(this);
		// childColumns.getChildColumnForeignKeys().add(this);
		for (Column column : childColumns) {
			column.getChildColumnForeignKeys().add(this);
		}
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

	public Table getChildTable() {
		return childTable;
	}

	public void setChildTable(Table childTable) {
		this.childTable = childTable;
	}

	public List<Column> getParentColumns() {
		return parentColumns;
	}

	public void setParentColumns(List<Column> parentColumns) {
		this.parentColumns = parentColumns;
	}

	public List<Column> getChildColumns() {
		return childColumns;
	}

	public void setChildColumns(List<Column> childColumns) {
		this.childColumns = childColumns;
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
