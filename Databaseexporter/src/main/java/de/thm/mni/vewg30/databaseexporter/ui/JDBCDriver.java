package de.thm.mni.vewg30.databaseexporter.ui;

import java.text.MessageFormat;

import de.thm.mni.vewg30.databaseexporter.exeptions.JDBCDriverDetectionException;

public enum JDBCDriver {

	MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql:"), //
	POSTRGRESQL("org.postgresql.Driver", "jdbc:postgresql:"), //
	JDBCODBC_BRIDGE("sun.jdbc.odbc.JdbcOdbcDriver", "jdbc:odbc:")//
	;

	private String jdbcDriverClassName;
	private String jdbcURLStart;

	private JDBCDriver(String jdbcDriverClassName, String jdbcURLStart) {
		this.jdbcDriverClassName = jdbcDriverClassName;
		this.jdbcURLStart = jdbcURLStart;
	}

	public String getJdbcDriverClassName() {
		return jdbcDriverClassName;
	}

	public String getJdbcURLStart() {
		return jdbcURLStart;
	}

	public static JDBCDriver getKnownDriverByURL(String url) throws JDBCDriverDetectionException{
		String trimmed = url.trim().toLowerCase();
		
		for(JDBCDriver value: values()){
			if(trimmed.startsWith(value.jdbcURLStart)){
				return value;
			}
		}
		throw new JDBCDriverDetectionException(MessageFormat.format("Cannot detect driver for url[{0}] use switch [{1}] to specify it manually", url,Switches.DRIVER.getSwitchLiteralLong()));
	}
}
