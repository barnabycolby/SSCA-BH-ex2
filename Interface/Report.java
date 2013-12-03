package ex2.Interface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import ex2.DBTeach2Connection.DBTeach2Connection;

public class Report {
	public static void produceReport() {
		System.out.println("Create a report");
		System.out.println("---------------");
		System.out.println("1. Please enter the year:");
		String yearString = System.console().readLine();
		int year = 0;
		try {
			year = Integer.parseInt(yearString);
		} catch (NumberFormatException ex) {
			year = 0;
		}
		System.out.println("2. Please enter the session:");
		System.out.println("   (May, August)");
		String sessionString = System.console().readLine();
		int sessionID = convertSessionStringToSessionID(sessionString);

		try {
			if (!printReport(year, sessionID)) {
				System.out.println("An error occurred.");
			}
		} catch (SQLException ex) {
			System.out.println("An error occurred.");
		}
	}

	private static boolean printReport(int year, int sessionID)
			throws SQLException {
		Connection conn = DBTeach2Connection.obtain();
		if (conn == null) {
			System.out.println("Failed to connect to the database.");
			return false;
		}

		String queryString = "SELECT t1.courseID,coursename,\"Number of Students\",\"Average mark\" "
				+ "FROM ("
				+ "SELECT Course.courseID,COUNT(mark) AS \"Number of Students\",AVG(mark) AS \"Average mark\" "
				+ "FROM Course "
				+ "LEFT JOIN Marks "
				+ "ON (Course.courseID=Marks.courseID AND year=? AND sessionID=?) "
				+ "GROUP BY Course.courseID"
				+ ")"
				+ " AS t1 "
				+ "INNER JOIN Course "
				+ "ON t1.courseID=Course.courseID "
				+ "WHERE NOT \"Number of Students\"=0 "
				+ "ORDER BY courseid ASC;";

		PreparedStatement query = conn.prepareStatement(queryString);
		query.setInt(1, year);
		query.setInt(2, sessionID);
		ResultSet result = query.executeQuery();

		prettyPrintHeader();

		while (result.next()) {
			// Extract the information
			String courseID = result.getString("courseID");
			String courseName = result.getString("coursename");
			String numberOfStudents = result.getString("Number of Students");
			float averageMark = result.getFloat("Average Mark");
			DecimalFormat df = new DecimalFormat("#.##");
			String averageMarkString = df.format(averageMark);

			prettyPrintCourseReport(courseID, courseName, numberOfStudents,
					averageMarkString);
		}

		return true;
	}

	private static String[] getColumnHeaders() {
		return new String[] { "Course ID", "Course Name", "Number of Students",
				"Average Mark" };
	}

	private static void prettyPrintCourseReport(String courseID,
			String courseName, String numberOfStudents, String averageMark) {
		String[] headers = getColumnHeaders();
		String[] data = { courseID, courseName, numberOfStudents, averageMark };

		boolean first = true;
		for (int i = 0; i < headers.length; i++) {
			if (data[i].length() < headers[i].length()) {
				data[i] = CommonFunctions.padString(data[i], headers[i].length()
						- data[i].length());
			}
			
			if(!first){
				System.out.print(" | ");
			} else {
				first = false;
			}
			System.out.print(data[i]);
		}
		
		System.out.println();
	}

	private static void prettyPrintHeader() {
		String[] headers = getColumnHeaders();
		String headerRow = headers[0];
		String equalsRow = CommonFunctions.repeatedCharacterString("=",
				headers[0].length());
		for (int i = 1; i < headers.length; i++) {
			headerRow += " | ";
			headerRow += headers[i];
			equalsRow += CommonFunctions.repeatedCharacterString("=",
					headers[i].length() + 3);
		}

		System.out.println();
		System.out.println(headerRow);
		System.out.println(equalsRow);
	}

	private static int convertSessionStringToSessionID(String sessionIDString) {
		if (sessionIDString.equals("May")) {
			return 1;
		} else if (sessionIDString.equals("August")) {
			return 2;
		} else {
			return 0;
		}
	}
}
