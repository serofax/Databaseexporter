package de.thm.mni.vewg30.databaseexporter.ui;


public enum Switches {
	DRIVER("--jdbcdriver",
			"Specify the jdbc-driver classname if it can't detected by the url"), //
	CREDENTIALS("--credentials", "--c",
			"--credentials username password; Specify the credentials "), //
	DDL_FILE(
			"--ddl-output-path",
			"--ddl",
			"Defines the path where the DDL-file will be created; The output on the console will be reduced by the ddl-commands use '--to-console' to print it anyways"), //
	DML_FILE(
			"--dml-output-path",
			"--dml",
			"Defines the path where the DML-file will be created; The output on the console will be reduced by the dml-commands use '--to-console' to print it anyways"), //
	TO_CONSOLE("--to-console", "--tc", "If you use "
			+ DDL_FILE.getSwitchLiteralLong() + " or "
			+ DML_FILE.getSwitchLiteralLong()
			+ " the content will be printed on the console")//
	;

	private String switchLiteralLong;
	private String switchLiteralShort;
	private String description;

	private Switches(String switchLiteral, String description) {
		this.switchLiteralLong = switchLiteral;
		this.description = description;
	}

	private Switches(String switchLiteral, String switchLiteralShort,
			String description) {
		this(switchLiteral, description);
		this.switchLiteralShort = switchLiteralShort;
	}

	public String getSwitchLiteralLong() {
		return switchLiteralLong;
	}

	public String getSwitchLiteralShort() {
		return switchLiteralShort;
	}

	public String getDescription() {
		return description;
	}

	public boolean isSwitch(String param) {

		return switchLiteralLong.equals(param)
				|| (switchLiteralShort != null && switchLiteralShort
						.equals(param));
	}

	public String getSwitchHelp() {
		String result = "";
		String start = "\t";
		if (switchLiteralShort != null) {
			start += switchLiteralShort + ", ";
		}
		start += switchLiteralLong;
		result = String.format("%-30s Desc.: %s", start, description);
		return result;
	}

}
