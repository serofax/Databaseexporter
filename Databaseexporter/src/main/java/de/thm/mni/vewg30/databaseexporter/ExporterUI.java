package de.thm.mni.vewg30.databaseexporter;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class ExporterUI {
	private static final Logger log = Logger.getLogger(ExporterUI.class);

	private String driver;
	private String urlPortDatabase;
	private String username = "root";
	private String password = "";
	private File outputFolder;

	public ExporterUI(String[] args) {
		printWelcome();
		parseArguments(args);
	}

	private void parseArguments(String[] args) {
		if (args.length < 4) {
			String error = "too few arguments";
			System.out.println(error);
			log.error(error);
			printHelp();
			System.exit(-1);
		}
		this.driver = args[0].trim();
		this.urlPortDatabase = args[1];
		this.outputFolder = new File(args[2]);
		if(args.length>=4){
			this.username = args[3];
		}
		if(args.length>=5){
			this.password = args[4];
		}
		if(log.isDebugEnabled()){
			log.debug("Arguments: [driver=" + driver + ", urlPortDatabase="
				+ urlPortDatabase + ", username=" + username + ", password="
				+ password + ", outputFolder=" + outputFolder + "]");
		}
	}

	private void printWelcome() {

		String welcomeString = MessageFormat.format(
				"This is {0} Version {1} created by {2}",
				Versioninformation.NAME.getValue(),
				Versioninformation.VERSION.getValue(),
				Versioninformation.AUTHOR.getValue());
		System.out.println(welcomeString);
		log.debug("printed welcome");

	}
	
	private void printHelp(){
		String help = "Usage: " + ExporterUI.class.getSimpleName() + " JDBCDriver Outputfolder UrlPortDatabase [Username Default:root] [Password Default:<empty>]";
				System.out.println(help);
		log.debug("printed help");
	}

	public static void main(String[] args) {
		log.info("Startup programm");
		log.info(MessageFormat.format("Arguments are [{0}]",
				Arrays.toString(args)));
		new ExporterUI(args);
	}

	@Override
	public String toString() {
		return "ExporterUI [driver=" + driver + ", urlPortDatabase="
				+ urlPortDatabase + ", username=" + username + ", password="
				+ password + ", outputFolder=" + outputFolder + "]";
	}	
}
