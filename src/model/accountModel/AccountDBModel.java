package model.accountModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import database.DBConst;

public class AccountDBModel {

	private Connection connection;
	private Statement stmt;
	private ResultSet rs;
	
	public AccountDBModel() throws SQLException {
		this.connection = DBConnection.getConnection();
		this.stmt = this.connection.createStatement();
	}
	
	public boolean isValidated(User user) throws SQLException {
		
		rs = this.stmt.executeQuery(
				"select * from "+DBConst.USER_TABLE+" where "+DBConst.EMAIL+"='" + user.getEmail() + "' and " + ""+DBConst.PASSWORD+"='" + user.getPassword() + "';");

		boolean isOk = false;
		while (rs.next()) {
			if (rs.getString("email").equals(user.getEmail()) && rs.getString("password").equals(user.getPassword())) {
				isOk = true;
			} else {
				isOk = false;
			}
		}

		connection.close();
		return isOk;
	}
	
	public int signUp(User user) throws SQLException {
		String insertUser = "insert into  "+DBConst.USER_TABLE+"("+DBConst.USER_NAME+", "+DBConst.EMAIL+", "+DBConst.PASSWORD+")"+"values ('"+ user.getUserName() + "','" + user.getEmail() + "','" + user.getPassword() + "')";
		return this.stmt.executeUpdate(insertUser);
	}
}
