package ex2.Interface;

import java.sql.*;

import ex2.DBTeach2Connection.DBTeach2Connection;

public class Transcript {
	public static void produceTranscript() throws SQLException {
		Connection conn = DBTeach2Connection.obtain();
		if (conn == null) {
			System.out.println("Failed to connect to the database.");
			return;
		}

		System.out.println("1. Please enter the students id:");
		String studentIDString = System.console().readLine();
		int studentID = 0;
		try {
			studentID = Integer.parseInt(studentIDString);
		} catch (NumberFormatException ex) {
			studentID = 0;
		}

		try {
			if (printBasicInformation(conn, studentID)) {
				System.out.println();
				if (!printMarksBreakdown(conn, studentID)) {
					System.out.println("An error occured.");
					return;
				}
			} else {
				System.out.println("An error occured.");
				return;
			}
		} catch (SQLException ex) {
			System.out.println("An error occured.");
			return;
		}
	}

	private static boolean printBasicInformation(Connection conn, int studentID)
			throws SQLException {
		String queryString = "SELECT titleString,forename,familyname,dateofbirth,sex,eMailAddress, postalAddress "
				+ "FROM ("
				+ "SELECT titleID,forename,familyname,dateofbirth,sex,eMailAddress,postalAddress "
				+ "FROM Student "
				+ "LEFT JOIN StudentContact "
				+ "ON Student.studentID=StudentContact.studentID "
				+ "WHERE Student.studentID=?) "
				+ "AS t1 "
				+ "INNER JOIN Titles ON t1.titleID=Titles.titleID;";

		PreparedStatement query = conn.prepareStatement(queryString);
		query.setInt(1, studentID);
		ResultSet result = query.executeQuery();

		if (result.next()) {
			String title = result.getString("titleString");
			String forename = result.getString("forename");
			String familyname = result.getString("familyname");
			String dateofbirth = result.getString("dateofbirth");
			String sex = result.getString("sex");
			String eMailAddress = result.getString("eMailAddress");
			String postalAddress = result.getString("postalAddress");

			if (eMailAddress == null) {
				eMailAddress = "";
			}
			if (postalAddress == null) {
				postalAddress = "";
			}

			System.out.println("Name:    " + title + " " + forename + " "
					+ familyname);
			System.out.println("DOB:     " + dateofbirth);
			System.out.println("Sex:     " + sex);
			System.out.println("Email:   " + eMailAddress);
			System.out.println("Address: " + postalAddress);

			return true;
		} else {
			return false;
		}
	}

	private static boolean printMarksBreakdown(Connection conn, int studentID)
			throws SQLException {
		String queryString = "SELECT year,courseID,coursename,mark,sessionstring,typestring "
				+ "FROM ("
				+ "SELECT year,courseID,courseName,mark,sessionString,typeID "
				+ "FROM ("
				+ "SELECT year,t1.courseID,courseName,mark,sessionID,typeID "
				+ "FROM ("
				+ "SELECT year,courseID,mark,sessionID,typeID "
				+ "FROM Marks "
				+ "WHERE studentid=?)"
				+ "AS t1 "
				+ "LEFT JOIN Course "
				+ "ON t1.courseID=Course.courseID)"
				+ "AS t2 "
				+ "LEFT JOIN Session "
				+ "ON t2.sessionID=Session.sessionID) "
				+ "AS t3 "
				+ "LEFT JOIN Type "
				+ "ON t3.typeID=Type.typeID "
				+ "ORDER BY year ASC";

		PreparedStatement query = conn.prepareStatement(queryString);
		query.setInt(1, studentID);
		ResultSet result = query.executeQuery();

		boolean first = true;
		while (result.next()) {
			// Separate the course years
			if (!first) {
				System.out.println();
			} else {
				first = false;
			}

			// Extract the information
			String year = result.getString("year");
			String courseID = result.getString("courseID");
			String courseName = result.getString("coursename");
			String mark = result.getString("mark");
			String session = result.getString("sessionstring");
			String type = result.getString("typestring");

			prettyPrintMarks(year, courseID, courseName, mark, session, type);
		}

		if (first)
			return false;

		return true;
	}

	private static void prettyPrintMarks(String year, String courseID,
			String courseName, String mark, String session, String type) {
		String[] headerRowArray = { "Course ID", "Course Name", "Mark",
				"Session", "Type" };
		String[] dataRowArray = { courseID, courseName, mark, session, type };

		for (int i = 0; i < headerRowArray.length; i++) {
			int lengthDifference = headerRowArray[i].length()
					- dataRowArray[i].length();

			if (lengthDifference > 0) {
				dataRowArray[i] = CommonFunctions.padString(dataRowArray[i], lengthDifference);
			} else if (lengthDifference < 0) {
				headerRowArray[i] = CommonFunctions.padString(headerRowArray[i],
						lengthDifference * -1);
			}
		}

		String headerRow = headerRowArray[0];
		for (int i = 1; i < headerRowArray.length; i++) {
			headerRow += " | ";
			headerRow += headerRowArray[i];
		}

		String dataRow = dataRowArray[0];
		for (int i = 1; i < dataRowArray.length; i++) {
			dataRow += " | ";
			dataRow += dataRowArray[i];
		}

		System.out.println(CommonFunctions.padString("Year " + year, dataRow.length() - 6));
		System.out.println(CommonFunctions.repeatedCharacterString("=", dataRow.length()));
		System.out.println(headerRow);
		System.out.println(CommonFunctions.repeatedCharacterString("-", dataRow.length()));
		System.out.println(dataRow);
	}

	public static String getEntry(String entry) {
		System.out.println("   The " + entry
				+ " entered was not valid, please try again.");
		return System.console().readLine();
	}
}
