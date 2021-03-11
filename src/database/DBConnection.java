package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static String url = "jdbc:mysql://localhost:3306/sumeldb?useSSL=false&useTimezone=true&serverTimezone=UTC";
	private static String user = "root";
	private static String password = "root";
	//public static Connection dbConnector;

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
	
	/*
	 * public static Connection getConnctionInstance() { if(dbConnector == null) {
	 * dbConnector = getConnection(); System.out.println(dbConnector+" is null if");
	 * return dbConnector; }else { System.out.println(dbConnector); return
	 * dbConnector; } }
	 */
	
//	public static void getConnectiond() {
//		Connection connection = null;
//		try {
//			connection = DriverManager.getConnection(url, user, password);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		dbConnector = connection;
//	}
}
