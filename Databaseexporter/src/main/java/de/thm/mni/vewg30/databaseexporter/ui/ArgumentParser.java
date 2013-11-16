package de.thm.mni.vewg30.databaseexporter.ui;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import de.thm.mni.vewg30.databaseexporter.exeptions.ArgumentParserExeption;
import de.thm.mni.vewg30.databaseexporter.exeptions.JDBCDriverDetectionException;

/**
 * NOT theadsafe
 * 
 * @author vincent
 * 
 */
public class ArgumentParser {
	private static final Logger log = Logger.getLogger(ArgumentParser.class);

	private List<String> args;
	private boolean toConsoleOnValidation = false;

	public ArgumentParser(String[] args) {
		this.args = new ArrayList<String>(Arrays.asList(args));
	}

	public Configuration getConfiguration() throws ArgumentParserExeption,
			JDBCDriverDetectionException {
		if (log.isDebugEnabled()) {
			log.debug(MessageFormat.format(
					"start getConfiguration with arguments[{0}]", args));
		}

		if (args.size() < 1) {
			String error = "too few arguments";
			throw new ArgumentParserExeption(error);
		}

		String url = getUrl();
		Configuration result = new Configuration(url);
		parseSwitches(result);
		validateConfiguration(result);

		if (log.isDebugEnabled()) {
			log.debug(MessageFormat.format(
					"stop getConfiguration with result [{0}]", result));
		}
		return result;
	}

	private void validateConfiguration(Configuration configuration)
			throws JDBCDriverDetectionException {
		if (configuration.getDriver() == null) {
			log.debug("user did not specified jdbcdriver; try to detect it");
			configuration.setDriver(JDBCDriver.getKnownDriverByURL(
					configuration.getUrlPortDatabase())
					.getJdbcDriverClassName());
		}
		if (toConsoleOnValidation) {
			configuration.setToConsoleDDL(true);
			configuration.setToConsoleDML(true);
			toConsoleOnValidation = false;
		}

	}

	private void parseSwitches(Configuration configuration)
			throws ArgumentParserExeption {

		ListIterator<String> iterator = args.listIterator();
		while (iterator.hasNext()) {
			String element = iterator.next().trim();
			if (Switches.DRIVER.isSwitch(element)) {
				iterator.remove();
				if (iterator.hasNext()) {
					String driver = iterator.next();
					configuration.setDriver(driver);
					iterator.remove();
					log.debug(MessageFormat.format(
							"User specify jdbcdriver [{0}]", driver));
					continue;
				} else {
					throw createExpectingAnotherLiteral(Switches.DRIVER);
				}
			}
			if (Switches.DDL_FILE.isSwitch(element)) {
				iterator.remove();
				if (iterator.hasNext()) {
					String path = iterator.next();
					File file = new File(path);
					configuration.setDdlFile(file);
					iterator.remove();
					continue;
				} else {
					throw createExpectingAnotherLiteral(Switches.DDL_FILE);
				}
			}

			if (Switches.DML_FILE.isSwitch(element)) {
				iterator.remove();
				if (iterator.hasNext()) {
					String path = iterator.next();
					File file = new File(path);
					configuration.setDmlFile(file);
					iterator.remove();
					continue;
				} else {
					throw createExpectingAnotherLiteral(Switches.DML_FILE);
				}
			}

			if (Switches.TO_CONSOLE.isSwitch(element)) {
				iterator.remove();
				toConsoleOnValidation = true;
				continue;
			}

			if (Switches.CREDENTIALS.isSwitch(element)) {
				iterator.remove();
				if (iterator.hasNext()) {
					String username = iterator.next();
					configuration.setUsername(username);
					iterator.remove();
					if (iterator.hasNext()) {
						String password = iterator.next();
						configuration.setPassword(password);
						iterator.remove();
						continue;
					}
				}
				throw createExpectingAnotherLiteral(Switches.CREDENTIALS);
			}
		}
		
		if(!args.isEmpty()){
			throw new ArgumentParserExeption(MessageFormat.format("Cannot handle switches or arguments [{0}]",args));
		}
		
	}

	private ArgumentParserExeption createExpectingAnotherLiteral(
			Switches switchObj) {
		return new ArgumentParserExeption(MessageFormat.format(
				"Expecting one or more literals for switch [{0}]", switchObj));
	}

	private String getUrl() {
		return args.remove(args.size() - 1);

	}

}
