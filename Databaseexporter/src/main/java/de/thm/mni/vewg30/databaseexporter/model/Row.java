package de.thm.mni.vewg30.databaseexporter.model;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Row {
	private HashMap<Column, Object> items = new LinkedHashMap<Column, Object>();

	public HashMap<Column, Object> getItems() {
		return items;
	}

	public void setItems(HashMap<Column, Object> items) {
		this.items = items;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Row))
			return false;
		Row other = (Row) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

}
