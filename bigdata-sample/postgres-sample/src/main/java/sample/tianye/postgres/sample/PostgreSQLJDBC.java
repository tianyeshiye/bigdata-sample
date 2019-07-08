package sample.tianye.postgres.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgreSQLJDBC {

	public static void main(String args[]) {

		int loopNum = 10000;
		for (int i = 0; i < loopNum; i++) {
			call(i * 1000);
		}
	}

	// 每次插入1000条 循环执行
	public static void call(int start) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM tableAAA  where time > '2019-07-03 12:07:51' limit 1000 offset "
							+ String.valueOf(start) + ";");
			while (rs.next()) {
				String itemA = rs.getString("itemA");
				System.out.println("itemA = " + itemA);
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