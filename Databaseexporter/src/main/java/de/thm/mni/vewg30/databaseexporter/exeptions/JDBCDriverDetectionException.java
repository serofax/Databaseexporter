package de.thm.mni.vewg30.databaseexporter.exeptions;

public class JDBCDriverDetectionException extends Exception {


	private static final long serialVersionUID = 2502749132994506789L;

	public JDBCDriverDetectionException() {
	}

	public JDBCDriverDetectionException(String message) {
		super(message);
	}

	public JDBCDriverDetectionException(Throwable cause) {
		super(cause);
	}

	public JDBCDriverDetectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public JDBCDriverDetectionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
