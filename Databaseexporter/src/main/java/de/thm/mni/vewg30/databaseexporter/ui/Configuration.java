package de.thm.mni.vewg30.databaseexporter.ui;

import java.io.File;

public class Configuration {

	private String driver;
	private String urlPortDatabase;
	private String username = "root";
	private String password = "";
	private File ddlFile;
	private File dmlFile;
	private boolean toConsoleDDL = true;
	private boolean toConsoleDML = true;

	public Configuration(String urlPortDatabase) {
		super();
		this.urlPortDatabase = urlPortDatabase;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrlPortDatabase() {
		return urlPortDatabase;
	}

	public void setUrlPortDatabase(String urlPortDatabase) {
		this.urlPortDatabase = urlPortDatabase;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public File getDdlFile() {
		return ddlFile;
	}

	public void setDdlFile(File ddlFile) {
		this.ddlFile = ddlFile;
		this.toConsoleDDL = false;
	}

	public File getDmlFile() {
		return dmlFile;
	}

	public void setDmlFile(File dmlFile) {
		this.dmlFile = dmlFile;
		this.toConsoleDML = false;
	}

	public boolean isToConsoleDDL() {
		return toConsoleDDL;
	}

	public void setToConsoleDDL(boolean toConsoleDDL) {
		this.toConsoleDDL = toConsoleDDL;
	}

	public boolean isToConsoleDML() {
		return toConsoleDML;
	}

	public void setToConsoleDML(boolean toConsoleDML) {
		this.toConsoleDML = toConsoleDML;
	}
	

	@Override
	public String toString() {
		return "Configuration [driver=" + driver + ", urlPortDatabase="
				+ urlPortDatabase + ", username=" + username + ", password=****, ddlFile=" + ddlFile + ", dmlFile=" + dmlFile
				+ ", toConsoleDDL=" + toConsoleDDL + ", toConsoleDML="
				+ toConsoleDML + "]";
	}
	
	

}
