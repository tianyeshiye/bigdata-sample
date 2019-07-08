package sample.postgres.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgreSQLJDBC {

	public static void main(String args[]) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");

			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM realtime_train_status_109  where time > '2019-07-03 12:07:51';");
			while (rs.next()) {
				String p_id = rs.getString("p_id");
				String train_id = rs.getString("train_id");
				String time = rs.getString("time");

				System.out.println("p_id = " + p_id);
				System.out.println("train_id = " + train_id);
				System.out.println("time = " + time);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
	}
}