package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static String url = "jdbc:mysql://localhost:3306/sumeldb?useSSL=false&useTimezone=true&serverTimezone=UTC";
	private static String user = "root";
	private static String password = "";

	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}
