package de.thm.mni.vewg30.databaseexporter;

public enum Versioninformation {
	NAME("Database Exporter"),AUTHOR("Vincent Elliott Wagner"),VERSION("0.1");
	
	private String value;
	
	private Versioninformation(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	

}
