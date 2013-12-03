package ex2.Interface;

import java.io.IOException;
import java.sql.SQLException;

public class Interface {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void main(String[] args) {
		System.setProperty("jdbc.drivers", "org.postgresql.Driver");

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("PostgreSQL Driver not found.");
			return;
		}

		// System.out.println("PostgreSQL driver registered.");

		// Check that the correct number of arguments were provided
		if (args.length != 1) {
			printUsageInstructions();
			return;
		}

		try {
			if (args[0].equals("-s")) {
				Register.registerNewStudent();
				return;
			} else if (args[0].equals("-m")){
				AddMarks.addMark();
				return;
			} else if (args[0].equals("-t")){
				Transcript.produceTranscript();
				return;
			} else if (args[0].equals("-r")){
				Report.produceReport();
			} else {
				printUsageInstructions();
				return;
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private static void printUsageInstructions() {
		System.out.println("Usage: Interface [mode]");
		System.out.println("Modes: -s Register a new student");
		System.out.println("       -m Add a mark for a student");
		System.out.println("       -t Produce a transcript for a student");
		System.out.println("       -r Product a report for a course year and session");
	}
}
