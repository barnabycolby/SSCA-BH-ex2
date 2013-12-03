package ex2.TableMaker;

import java.sql.*;

public class Tables {
	public static void create(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();

		String prefix = "Creating tables...";

		// Create tables
		try {
			System.out.print(prefix + "Titles");
			Tables.createTitles(stmt, conn, prefix);
			System.out.print("\r" + prefix + "Student");
			Tables.createStudent(stmt, conn, prefix);
			System.out.print("\r" + prefix + "Lecturer");
			Tables.createLecturer(stmt, conn, prefix);
			System.out.print("\r" + prefix + "Course  ");
			Tables.createCourse(stmt, conn, prefix);
			System.out.print("\r" + prefix + "Session");
			Tables.createSession(stmt, conn, prefix);
			System.out.print("\r" + prefix + "Type   ");
			Tables.createType(stmt, conn, prefix);
			System.out.print("\r" + prefix + "Marks");
			Tables.createMarks(stmt, conn, prefix);
			System.out.print("\r" + prefix + "StudentContact");
			Tables.createStudentContact(stmt, conn, prefix);
			System.out.print("\r" + prefix + "NextOfKin     ");
			Tables.createNextOfKin(stmt, conn, prefix);
			System.out.println("\r" + prefix + "Done.    ");
		} catch (TableAlreadyExistsException ex) {
			// Destroy current tables
			Tables.destroy(conn, true);

			// Create the new ones
			Tables.create(conn);
		}
	}

	private static void createStudent(Statement stmt, Connection conn,
			String prefix) throws SQLException, TableAlreadyExistsException {
		String query = "CREATE TABLE Student (" + "studentID   SERIAL,"
				+ "titleID     INTEGER," + "forename    VARCHAR(35),"
				+ "familyname  VARCHAR(35)," + "dateOfBirth DATE,"
				+ "sex         VARCHAR(6)," + "PRIMARY KEY(studentID),"
				+ "FOREIGN KEY(titleID) REFERENCES Titles(titleID),"
				+ "CHECK((sex='Male' OR sex='Female')"
				+ "AND forename NOT LIKE '%[^A-Z -]%'"
				+ "AND familyname NOT LIKE '%[^A-Z -]%')" + ")";
		Tables.createTable(stmt, conn, query, prefix);
	}

	private static void createLecturer(Statement stmt, Connection conn,
			String prefix) throws SQLException, TableAlreadyExistsException {
		String query = "CREATE TABLE Lecturer (" + "lecturerID SERIAL,"
				+ "titleID    INTEGER," + "forename   VARCHAR(35),"
				+ "familyname VARCHAR(35)," + "PRIMARY KEY(lecturerID),"
				+ "FOREIGN KEY(titleID) REFERENCES Titles(titleID),"
				+ "CHECK(forename NOT LIKE '%[^A-Z -]%'"
				+ "AND familyname NOT LIKE '%[^A-Z -]%')" + ")";
		Tables.createTable(stmt, conn, query, prefix);
	}

	private static void createCourse(Statement stmt, Connection conn,
			String prefix) throws SQLException, TableAlreadyExistsException {
		String query = "CREATE TABLE Course (" + "courseID SERIAL,"
				+ "coursename        VARCHAR(50),"
				+ "coursedescription VARCHAR(250),"
				+ "lecturerID        INTEGER NOT NULL,"
				+ "PRIMARY KEY(courseID),"
				+ "FOREIGN KEY(lecturerID) REFERENCES Lecturer(lecturerID)"
				+ ")";
		Tables.createTable(stmt, conn, query, prefix);
	}

	private static void createMarks(Statement stmt, Connection conn,
			String prefix) throws SQLException, TableAlreadyExistsException {
		String query = "CREATE TABLE Marks (" + "studentID INTEGER,"
				+ "CourseID  INTEGER NOT NULL," + "year      INTEGER NOT NULL,"
				+ "sessionID INTEGER NOT NULL," + "typeID    INTEGER NOT NULL,"
				+ "mark      INTEGER NOT NULL," + "notes     VARCHAR(300),"
				+ "FOREIGN KEY(typeID) REFERENCES Type(typeID),"
				+ "FOREIGN KEY(sessionID) REFERENCES Session(sessionID),"
				+ "FOREIGN KEY(CourseID) REFERENCES Course(courseID),"
				+ "FOREIGN KEY(studentID) REFERENCES Student(studentID) ON DELETE CASCADE,"
				+ "CHECK((mark BETWEEN 0 AND 100) AND (year BETWEEN 1 AND 5))" + ")";
		Tables.createTable(stmt, conn, query, prefix);
	}

	private static void createStudentContact(Statement stmt, Connection conn,
			String prefix) throws SQLException, TableAlreadyExistsException {
		String query = "CREATE TABLE StudentContact ("
				+ "studentID     INTEGER," + "eMailAddress  VARCHAR(254),"
				+ "postalAddress VARCHAR(300),"
				+ "FOREIGN KEY(studentID) REFERENCES Student(studentID) ON DELETE CASCADE,"
				+ "CHECK(eMailAddress IS NOT NULL"
				+ " OR postalAddress IS NOT NULL)" + ")";
		Tables.createTable(stmt, conn, query, prefix);
	}

	private static void createNextOfKin(Statement stmt, Connection conn,
			String prefix) throws SQLException, TableAlreadyExistsException {
		String query = "CREATE TABLE NextOfKin (" + "studentID     INTEGER,"
				+ "eMailAddress  VARCHAR(254)," + "postalAddress VARCHAR(300),"
				+ "FOREIGN KEY(studentID) REFERENCES Student(studentID) ON DELETE CASCADE,"
				+ "CHECK(eMailAddress IS NOT NULL"
				+ " OR postalAddress IS NOT NULL)" + ")";
		Tables.createTable(stmt, conn, query, prefix);
	}

	private static void createTitles(Statement stmt, Connection conn,
			String prefix) throws SQLException, TableAlreadyExistsException {
		String query = "CREATE TABLE Titles (" + "titleID     SERIAL,"
				+ "titleString VARCHAR(4) NOT NULL UNIQUE,"
				+ "PRIMARY KEY(titleID)" + ")";
		Tables.createTable(stmt, conn, query, prefix);
	}

	private static void createSession(Statement stmt, Connection conn,
			String prefix) throws SQLException, TableAlreadyExistsException {
		String query = "CREATE TABLE Session (" + "sessionID SERIAL,"
				+ "sessionString VARCHAR(6) NOT NULL UNIQUE,"
				+ "PRIMARY KEY(sessionID)" + ")";
		Tables.createTable(stmt, conn, query, prefix);
	}

	private static void createType(Statement stmt, Connection conn,
			String prefix) throws SQLException, TableAlreadyExistsException {
		String query = "CREATE TABLE Type (" + "typeID SERIAL,"
				+ "typeString VARCHAR(6) NOT NULL UNIQUE,"
				+ "PRIMARY KEY(typeID)" + ")";
		Tables.createTable(stmt, conn, query, prefix);
	}

	private static void createTable(Statement stmt, Connection conn,
			String query, String prefix) throws SQLException,
			TableAlreadyExistsException {
		try {
			stmt.executeUpdate(query);
		} catch (SQLException ex) {
			if (ex.getSQLState().equals("42P07")) {
				// Delete the old table
				System.out.println("\r" + prefix
						+ "database needs cleaning!");
				throw new TableAlreadyExistsException();
			} else {
				throw ex;
			}
		}
	}

	public static void destroy(Connection conn, Boolean cleaning)
			throws SQLException {
		Statement stmt = conn.createStatement();

		String prefix = "";
		if (cleaning) {
			prefix = "Cleaning database...";
		} else {
			prefix = "Dropping tables...";
		}

		String[] tableNames = { "Marks", "Course", "StudentContact",
				"NextOfKin", "Lecturer", "Student", "Titles", "Session", "Type" };

		Tables.destroyTables(stmt, tableNames, prefix);

		System.out.println("\r" + prefix + "Done.");
	}

	private static void destroyTables(Statement stmt, String[] tableNames,
			String prefix) throws SQLException {
		for (int i = 0; i < tableNames.length; i++) {
			try {
				String query = "DROP TABLE " + tableNames[i];
				int numberOfSpaces = 14 - tableNames[i].length();
				String output = "\r" + prefix + tableNames[i];
				for(int j = 0; j < numberOfSpaces; j++)
					output += " ";
				System.out.print(output);
				stmt.executeUpdate(query);
			} catch (SQLException ex) {
				if (ex.getSQLState().equals("42P01")) {
					// Ignore
				} else {
					throw ex;
				}
			}
		}
	}
}
