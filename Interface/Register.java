package ex2.Interface;

import java.sql.*;

import ex2.DBTeach2Connection.DBTeach2Connection;

public class Register {
	public static void registerNewStudent() throws SQLException {
		System.out.println("Register a new student");
		System.out.println("----------------------");
		System.out.println("1. Please enter the students title:");
		System.out.println("(Ms, Miss, Mrs, Rev, Dr, Prof, Hon, Gov, Mr, Fr)");
		String title = System.console().readLine();
		int titleID = convertTitleStringToTitleID(title);
		System.out.println("2. Please enter the students forename:");
		String forename = System.console().readLine();
		System.out.println("3. Please enter the students surname:");
		String surname = System.console().readLine();
		System.out
				.println("4. Please enter the students date of birth (yyyy-mm-dd):");
		String dobString = System.console().readLine();
		System.out
				.println("5. Please enter the students sex (\"Male\" or \"Female\")");
		String sex = System.console().readLine();

		System.out.print("Adding student...");
		if (addNewStudent(titleID, forename, surname, dobString, sex)) {
			System.out.println("\rAdding student...Done.");
		}
	}

	private static Boolean addNewStudent(int titleID, String forename,
			String surname, String dobString, String sex) throws SQLException {
		Connection conn = DBTeach2Connection.obtain();
		if (conn != null) {
			// System.out.println("Database accessed.");
		} else {
			System.out.println("Failed to make connection.");
			return false;
		}

		try {
			String queryString = "INSERT INTO Student (titleID,forename,familyName,dateOfBirth,sex) VALUES (?,?,?,?,?)";
			PreparedStatement query = conn.prepareStatement(queryString);
			query.setInt(1, titleID);
			query.setString(2, forename);
			query.setString(3, surname);
			query.setString(4, dobString);
			query.setString(5, sex);
			query.executeUpdate();

			return true;
		} catch (SQLException ex) {
			System.out.println("An error occured.");
			return false;
		} finally {
			conn.close();
		}
	}

	private static int convertTitleStringToTitleID(String title) {
		String[] titles = getTitles();

		for (int i = 0; i < titles.length; i++) {
			if (title.equals(titles[i]))
				return (i + 1);
		}

		return 0;
	}

	private static String[] getTitles() {
		return new String[] { "Ms", "Miss", "Mrs", "Rev", "Dr", "Prof", "Hon",
				"Gov", "Mr", "Fr" };
	}
}
