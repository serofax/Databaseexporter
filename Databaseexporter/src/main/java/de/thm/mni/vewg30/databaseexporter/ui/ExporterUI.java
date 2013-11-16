package de.thm.mni.vewg30.databaseexporter.ui;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Arrays;

import org.apache.log4j.Logger;

import de.thm.mni.vewg30.databaseexporter.ddl.DDLModelEnhancer;
import de.thm.mni.vewg30.databaseexporter.ddl.DDLRawModelExtractor;
import de.thm.mni.vewg30.databaseexporter.dml.DMLModelExtractor;
import de.thm.mni.vewg30.databaseexporter.exeptions.ArgumentParserExeption;
import de.thm.mni.vewg30.databaseexporter.exeptions.JDBCDriverDetectionException;
import de.thm.mni.vewg30.databaseexporter.model.Database;
import de.thm.mni.vewg30.databaseexporter.writer.DDLDatabaseWriter;
import de.thm.mni.vewg30.databaseexporter.writer.DMLDatabaseWriter;
import de.thm.mni.vewg30.databaseexporter.writer.MultiWriter;

public class ExporterUI {
	private static final Logger log = Logger.getLogger(ExporterUI.class);

	private Configuration configuration;

	public ExporterUI(String[] args) {
		printWelcome();
		parseArguments(args);
	}

	private void parseArguments(String[] args) {
		ArgumentParser parser = new ArgumentParser(args);
		if(Arrays.asList(args).contains(Switches.HELP.getSwitchLiteralLong())){
			printHelp();
			System.exit(0);
		}
		
		
		try {
			configuration = parser.getConfiguration();			
		} catch (ArgumentParserExeption e) {
			log.error("error while parsing arguments", e);
			printError(e.getMessage());
			printHelp();
			System.exit(-1);
		} catch (JDBCDriverDetectionException e) {
			log.error("error while detecting jdbcdriver", e);
			printError(e.getMessage());
			printHelp();
			System.exit(-1);
		}

		if (log.isDebugEnabled()) {
			log.debug("Configuration is " + configuration.toString());
		}
	}

	private void printWelcome() {
		String title = getClass().getPackage().getImplementationTitle();
		String version = getClass().getPackage().getImplementationVersion();
		String vendor = getClass().getPackage().getImplementationVendor();

		String welcomeString = MessageFormat.format(
				"This is {0} Version {1} created by {2}",
				getUndefinedWarningVariableString(title),
				getUndefinedWarningVariableString(version),
				getUndefinedWarningVariableString(vendor));
		System.out.println(welcomeString);
		log.debug("printed welcome");
	}

	private String getUndefinedWarningVariableString(String text) {
		if (text != null) {
			return text;
		} else {
			log.warn("This information is defined if the app is packed in a jar");
			return "UNDEFINED";
		}
	}

	private void printHelp() {
		String help = "Usage: " + ExporterUI.class.getSimpleName()
				+ " (switches) jdbcdatabaseurl";
		System.out.println(help);
		System.out.println();
		System.out.println("Switches:");
		for (Switches s : Switches.values()) {
			System.out.println(s.getSwitchHelp());
		}

		log.debug("printed help");
	}

	private void execute() {
		try {
			Class.forName(configuration.getDriver());
			log.debug("loaded driver");
		} catch (ClassNotFoundException e) {
			printFatalError(MessageFormat.format(
					"Cannot find JDBCDriverClass[{0}] in classpath",
					configuration.getDriver()));
		}
		Connection con = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(
					configuration.getUrlPortDatabase(),
					configuration.getUsername(), configuration.getPassword());
			log.info("connection established");

			DatabaseMetaData metaData = con.getMetaData();

			log.info("start extracting model");
			DDLRawModelExtractor extractor = new DDLRawModelExtractor(metaData);
			DDLModelEnhancer enhancer = new DDLModelEnhancer(metaData);
			Database database = extractor.getDatabase();
			enhancer.enhanceDatabase(database);
			DMLModelExtractor dmlExtractor = new DMLModelExtractor(con);
			database = dmlExtractor.getDatabaseWithData(database);
			log.info("model extracted");

			writeToDDLFile(database);
			writeToDMLFile(database);

			log.info("everything exported; export is finished");
			System.out.println("Export is finished. Good Bye");

		} catch (SQLException e) {
			printFatalError(e.getMessage(),e);
		} finally {
			try {

				if (rs != null) {
					rs.close();
				}

				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				printFatalError(e.getMessage(),e);
			}
		}

	}

	private void writeToDDLFile(Database database) {
		log.debug("start writeToDDLFile");
		// DO NOT CLOSE ONLY FLUSH IT
		DDLDatabaseWriter writer = null;
		// DO NOT CLOSE ONLY FLUSH IT
		OutputStreamWriter systemOutWriter = new OutputStreamWriter(System.out);
		// CLOSE THIS ONE
		FileWriter fileWriter = null;
		try {
			if (configuration.getDdlFile() != null) {
				configuration.getDdlFile().getParentFile().mkdirs();
				configuration.getDdlFile().createNewFile();
				fileWriter = new FileWriter(configuration.getDdlFile());
				if (configuration.isToConsoleDDL()) {
					MultiWriter multiWriter = new MultiWriter(new Writer[] {
							fileWriter, systemOutWriter });
					writer = new DDLDatabaseWriter(multiWriter);
				} else {
					writer = new DDLDatabaseWriter(fileWriter);
				}
			} else {
				writer = new DDLDatabaseWriter(systemOutWriter);
			}
			writer.writeDatabase(database);

		} catch (IOException e) {
			printFatalError(e.getMessage(), e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					printFatalError(e.getMessage(), e);
				}
			}
		}
		log.debug("stop writeToDDLFile");
	}

	private void writeToDMLFile(Database database) {
		log.debug("start writeToDMLFile");
		// DO NOT CLOSE ONLY FLUSH IT
		DMLDatabaseWriter writer = null;
		// DO NOT CLOSE ONLY FLUSH IT
		OutputStreamWriter systemOutWriter = new OutputStreamWriter(System.out);
		// CLOSE THIS ONE
		FileWriter fileWriter = null;
		try {
			if (configuration.getDmlFile() != null) {
				configuration.getDmlFile().getParentFile().mkdir();
				configuration.getDmlFile().createNewFile();
				fileWriter = new FileWriter(configuration.getDmlFile());
				if (configuration.isToConsoleDDL()) {
					MultiWriter multiWriter = new MultiWriter(new Writer[] {
							fileWriter, systemOutWriter });
					writer = new DMLDatabaseWriter(multiWriter);
				} else {
					writer = new DMLDatabaseWriter(fileWriter);
				}
			} else {
				writer = new DMLDatabaseWriter(systemOutWriter);
			}
			writer.writeDatabase(database);

		} catch (IOException e) {
			printFatalError(e.getMessage(), e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					printFatalError(e.getMessage(), e);
				}
			}
		}
		log.debug("stop writeToDMLFile");
	}

	private void printError(String message) {
		log.error(message);
		System.out.println("ERROR: " + message);
	}

	private void printError(String message, Throwable t) {
		log.error(message, t);
		System.out.println("ERROR: " + message);
	}

	private void printFatalError(String message) {
		printError(message);
		System.out.println("Program will be terminated");
		System.exit(-1);
	}

	private void printFatalError(String message, Throwable t) {
		printError(message, t);
		System.out.println("Program will be terminated");
		System.exit(-1);
	}

	public static void main(String[] args) {
		log.info("Startup programm");
		log.info(MessageFormat.format("Arguments are [{0}]",
				Arrays.toString(args)));
		ExporterUI exporter = new ExporterUI(args);
		exporter.execute();
	}

}
