package de.thm.mni.vewg30.databaseexporter.exeptions;

public class ArgumentParserExeption extends Exception {

	private static final long serialVersionUID = 4147719109250162878L;

	public ArgumentParserExeption() {
	}

	public ArgumentParserExeption(String message) {
		super(message);
	}

	public ArgumentParserExeption(Throwable cause) {
		super(cause);
	}

	public ArgumentParserExeption(String message, Throwable cause) {
		super(message, cause);
	}

	public ArgumentParserExeption(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
