package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.accountModel.User;

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
			if (rs.getString(DBConst.EMAIL).equals(user.getEmail()) && rs.getString(DBConst.PASSWORD).equals(user.getPassword())) {
				isOk = true;
				User.userId = rs.getInt(DBConst.USER_ID);
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
	
	public int getLatestUserId() throws SQLException {
		rs = this.stmt.executeQuery("select count(*) as latestUserId from "+DBConst.USER_TABLE+";");
		rs.next();
		return rs.getInt("latestUserId");
	}
}
