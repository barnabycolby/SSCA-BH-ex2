package ex2.Interface;

import java.sql.*;
import ex2.DBTeach2Connection.DBTeach2Connection;

public class AddMarks {
	public static void addMark() throws SQLException {
		Connection conn = DBTeach2Connection.obtain();
		if (conn == null) {
			System.out.println("Failed to connect to the database.");
			return;
		}

		System.out.println("Add marks for a student");
		System.out.println("-----------------------");
		System.out.println("1. Please enter the students id:");
		String studentIDString = System.console().readLine();
		int studentID = 0;
		try {
			studentID = Integer.parseInt(studentIDString);
		} catch (NumberFormatException ex) {
			studentID = 0;
		}

		System.out.println("2. Please enter the course id:");
		String courseIDString = System.console().readLine();
		int courseID = 0;
		try {
			courseID = Integer.parseInt(courseIDString);
		} catch (NumberFormatException ex) {
			courseID = 0;
		}

		System.out.println("3. Please enter the year:");
		String yearString = System.console().readLine();
		int year = 0;
		try {
			year = Integer.parseInt(yearString);
		} catch (NumberFormatException ex) {
			year = 0;
		}

		System.out.println("4. Please enter the session (May, August):");
		String sessionString = System.console().readLine();
		int sessionID = convertSessionStringToSessionID(sessionString);

		System.out.println("5. Please enter the type (Normal, Resit, Repeat):");
		String typeString = System.console().readLine();
		int typeID = convertTypeStringToTypeID(typeString);

		System.out.println("6. Please enter the mark (0-100):");
		String markString = System.console().readLine();
		int mark = -1;
		try {
			mark = Integer.parseInt(markString);
		} catch (NumberFormatException ex) {
			mark = -1;
		}

		System.out.println("7. Please enter any notes or press enter to skip:");
		String notes = System.console().readLine();

		System.out.print("Adding mark...");
		if (addNewMark(studentID, courseID, year, sessionID, typeID, mark,
				notes)) {
			System.out.println("\rAdding mark...Done.");
		}
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

	private static int convertTypeStringToTypeID(String typeString) {
		if (typeString.equals("Normal")) {
			return 1;
		} else if (typeString.equals("Resit")) {
			return 2;
		} else if (typeString.equals("Repeat")) {
			return 3;
		} else {
			return 0;
		}
	}

	private static boolean addNewMark(int studentID, int courseID, int year,
			int sessionID, int typeID, int mark, String notes) {
		Connection conn = DBTeach2Connection.obtain();
		if (conn != null) {
			// System.out.println("Database accessed.");
		} else {
			System.out.println("Failed to make connection.");
			return false;
		}

		try {
			String queryString = "INSERT INTO Marks VALUES (?,?,?,?,?,?,?)";
			PreparedStatement query = conn.prepareStatement(queryString);
			query.setInt(1, studentID);
			query.setInt(2, courseID);
			query.setInt(3, year);
			query.setInt(4, sessionID);
			query.setInt(5, typeID);
			query.setInt(6, mark);
			query.setString(7, notes);
			query.executeUpdate();

			return true;
		} catch (SQLException ex) {
			System.out.println("An error occured.");
			return false;
		}
	}
}