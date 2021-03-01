package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import database.DBConst;

public class UserLogin {
	private Connection connection;
	private Statement stmt;
	private ResultSet rs;

	public Boolean isValidated(String email, String password) throws SQLException {
		connection = DBConnection.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(
				"select * from "+DBConst.USER_TABLE+" where "+DBConst.EMAIL+"='" + email + "' and " + ""+DBConst.PASSWORD+"='" + password + "';");

		boolean isOk = false;
		while (rs.next()) {
			if (rs.getString("email").equals(email) && rs.getString("password").equals(password)) {
				isOk = true;
			} else {
				isOk = false;
			}
		}

		connection.close();
		return isOk;
	}
}
