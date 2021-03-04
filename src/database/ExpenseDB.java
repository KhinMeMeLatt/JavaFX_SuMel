package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import model.Expense;
import model.accountModel.User;

public class ExpenseDB {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private Statement stmt;
	private ResultSet rs;
	
	public ExpenseDB() {
		this.connection = DBConnection.getConnection();
	}
	
	public void insertExpense(Expense expense) throws SQLException {
		String insertRow = "INSERT INTO "+DBConst.EXPENSE_TABLE+"("+DBConst.EXPENSE_NAME+","
							+DBConst.EXPENSE_CATEGORY+","+DBConst.EXPENSE_AMOUNT+","+DBConst.SPEND_AT+","
							+DBConst.EXPENSE_USER_ID+")"
							+"VALUES(?,?,?,?,?)";
		this.preparedStatement = connection.prepareStatement(insertRow);
		
		this.preparedStatement.setString(1, expense.getExpenseName());
		this.preparedStatement.setString(2, expense.getExpenseCategory());
		this.preparedStatement.setInt(3, expense.getExpenseAmount());
		
		LocalDate date = LocalDate.parse(expense.getSpendAt());
		Date publishedDate = Date.valueOf(date);
		this.preparedStatement.setDate(4, publishedDate);
		this.preparedStatement.setInt(5, User.userId);
		
		this.preparedStatement.executeUpdate();
	}
	
	public void selectTargetExpense() throws SQLException {
		this.stmt = this.connection.createStatement();
		this.rs = this.stmt.executeQuery("SELECT "+DBConst.TARGET_EXPENSE+" FROM "+DBConst.USER_TABLE+" WHERE "+DBConst.USER_ID+"='"+User.userId+"'");
		this.rs.next();
		User.expectedExpense = this.rs.getInt("targetExpense");
	}
}
