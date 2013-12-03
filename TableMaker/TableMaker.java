package ex2.TableMaker;

import java.io.IOException;

import ex2.DBTeach2Connection.*;

import java.sql.Connection;
import java.sql.SQLException;

public class TableMaker {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void main(String[] args) throws SQLException, IOException {
		System.setProperty("jdbc.drivers", "org.postgresql.Driver");

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("PostgreSQL Driver not found.");
			return;
		}

//		System.out.println("PostgreSQL driver registered.");

		Connection conn = DBTeach2Connection.obtain();
		if (conn != null) {
//			System.out.println("Database accessed.");
		} else {
			System.out.println("Failed to make connection.");
			return;
		}

		try {
			Tables.create(conn);
			Data.add(conn);

			System.out.print("\nPress any key to drop tables...");
			System.in.read();

			conn = DBTeach2Connection.obtain();
			if (conn != null) {
//				System.out.println("Database accessed.");
			} else {
				System.out.println("Failed to make connection.");
				return;
			}

			Tables.destroy(conn, false);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			conn.close();
		}
	}
}
