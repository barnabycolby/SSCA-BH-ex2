package ex2.DBTeach2Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTeach2Connection {
	private static username = "";
	private static password = "";

	public static Connection obtain() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:postgresql://dbteach2.cs.bham.ac.uk/bxc263", username, password);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return conn;
	}
}
