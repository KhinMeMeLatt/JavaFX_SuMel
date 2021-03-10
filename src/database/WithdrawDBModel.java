package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import alert.AlertMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Expense;
import model.accountModel.User;
import model.subuModel.SaveAndWithdrawHistory;
import model.subuModel.Withdraw;

public class WithdrawDBModel {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private Statement stmt;
	private ResultSet rs;
	private String query;
	private SaveAndWithdrawHistory swh;
	
	
	public WithdrawDBModel() {
		this.connection = DBConnection.getConnection();
	}
	
	public void insertExpense(Expense expense) {
		this.query = "INSERT INTO "+DBConst.EXPENSE_TABLE+"("+DBConst.EXPENSE_NAME+","
							+DBConst.EXPENSE_CATEGORY+","+DBConst.EXPENSE_AMOUNT+","+DBConst.SPEND_AT+","
							+DBConst.EXPENSE_USER_ID+")"
							+"VALUES(?,?,?,?,?)";
		try {
			this.preparedStatement = connection.prepareStatement(this.query);
			this.preparedStatement.setString(1, expense.getExpenseName());
			this.preparedStatement.setString(2, expense.getExpenseCategory());
			this.preparedStatement.setInt(3, expense.getExpenseAmount());
			
			LocalDate date = LocalDate.parse(expense.getSpendAt());
			Date spendAt = Date.valueOf(date);
			this.preparedStatement.setDate(4, spendAt);
			this.preparedStatement.setInt(5, User.userId);
			
			this.preparedStatement.executeUpdate();
			AlertMaker.showAlert(AlertType.INFORMATION,"Successful Message", null, "Expenses are recorded successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			AlertMaker.showAlert(AlertType.ERROR,"Error", "Error", "Expenses record process Failed!");
			e.printStackTrace();
		}
	
	}
	
	public void selectTargetExpense() throws SQLException {
		this.stmt = this.connection.createStatement();
		this.rs = this.stmt.executeQuery("SELECT "+DBConst.TARGET_EXPENSE+" FROM "+DBConst.USER_TABLE
										+" WHERE "+DBConst.USER_ID+"='"+User.userId+"'");
		this.rs.next();
		User.expectedExpense = this.rs.getInt(DBConst.TARGET_EXPENSE);
	}
	
	private void selectCategoryAmount() throws SQLException {
		this.stmt = this.connection.createStatement();
		this.rs = this.stmt.executeQuery("SELECT "+DBConst.EXPENSE_CATEGORY+", sum("+DBConst.EXPENSE_AMOUNT+") AS totalAmount "
										+" FROM "+DBConst.EXPENSE_TABLE
										+" WHERE userId='"+User.userId
										+"' GROUP BY "+DBConst.EXPENSE_CATEGORY+";");
	}
	
	// for pie chart
	public ObservableList<Data> selectWithCategory() throws SQLException {
		ObservableList<Data> pieChartData = FXCollections.observableArrayList();
		selectCategoryAmount();
		while(this.rs.next()) {
			pieChartData.add(new Data(rs.getString(DBConst.EXPENSE_CATEGORY), rs.getInt("totalAmount")));
		}
		return pieChartData;
	}
	
	//for expense panel
	public List<Expense> getCategoryAmount() {
		List<Expense> expenseList = new ArrayList<Expense>();
		try {
			selectCategoryAmount();
			while(this.rs.next()) {
				expenseList.add(new Expense(rs.getString(DBConst.EXPENSE_CATEGORY), rs.getInt("totalAmount")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return expenseList;
	}
	
	//History
	public ObservableList<Expense> show(String type, String value) throws SQLException{
		ObservableList<Expense> expenseList = FXCollections.observableArrayList();
		this.stmt = this.connection.createStatement();
		if(type=="all") {
			this.query = "SELECT * FROM "+DBConst.EXPENSE_TABLE
					+" WHERE "+DBConst.EXPENSE_USER_ID+"='"+User.userId
					+"' ORDER BY "+DBConst.SPEND_AT+" DESC;";
		}else {
			this.query = "SELECT * FROM "+DBConst.EXPENSE_TABLE
					+" WHERE "+DBConst.EXPENSE_USER_ID+"='"+User.userId
					+"' AND "+type+"='"+value+"' ORDER BY "+DBConst.SPEND_AT+" DESC;";
		}
		this.rs = this.stmt.executeQuery(this.query);
		while(this.rs.next()) {
			expenseList.add(new Expense(rs.getInt("expenseId"),
										rs.getString(DBConst.EXPENSE_NAME), 
										rs.getString(DBConst.EXPENSE_CATEGORY), 
										rs.getInt(DBConst.EXPENSE_AMOUNT), 
										rs.getString(DBConst.SPEND_AT)));
		}
		return expenseList;
	}
	
	public ObservableList<String> selectCategory() throws SQLException{
		ObservableList<String> category = FXCollections.observableArrayList();
		this.stmt = this.connection.createStatement();
		this.rs = this.stmt.executeQuery("SELECT DISTINCT "+DBConst.EXPENSE_CATEGORY+" FROM "+DBConst.EXPENSE_TABLE);
		while(this.rs.next()) {
			category.add(rs.getString(DBConst.EXPENSE_CATEGORY));
		}
		if(!category.contains("Travel Expense")) {
			category.add("Travel Expense");
		}
		if(!category.contains("Food")){
			category.add("Food");
		}
		if(!category.contains("Electricity bill")){
			category.add("Electricity bill");
		}
		if(!category.contains("Clothes")){
			category.add("Clothes");
		}
		if(!category.contains("Others")){
			category.add("Others");
		}
		return category;
	}
	
	public void deleteExpense(int expenseId) {
		try {
			this.stmt = this.connection.createStatement();
			this.stmt.executeUpdate("DELETE FROM "+DBConst.EXPENSE_TABLE+" WHERE "+DBConst.EXPENSE_ID+" ='"+expenseId+"';");
			AlertMaker.showAlert(AlertType.INFORMATION,"Successful Message", null, "A record is deleted successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			AlertMaker.showAlert(AlertType.ERROR,"Error", "Error", "Record deletion process Failed!");
			e.printStackTrace();
		}
	}
	
	public void updateExpense(Expense expense) {
		this.query = "UPDATE "+DBConst.EXPENSE_TABLE+" SET "+DBConst.EXPENSE_NAME+"=?,"
							+DBConst.EXPENSE_CATEGORY+"=?,"+DBConst.EXPENSE_AMOUNT+"=?,"+DBConst.SPEND_AT+"=?"
							+"WHERE "+DBConst.EXPENSE_ID+" =?";
		try {
			this.preparedStatement = connection.prepareStatement(this.query);
			this.preparedStatement.setString(1, expense.getExpenseName());
			this.preparedStatement.setString(2, expense.getExpenseCategory());
			this.preparedStatement.setInt(3, expense.getExpenseAmount());
			
			LocalDate date = LocalDate.parse(expense.getSpendAt()).plus(1,ChronoUnit.DAYS);
			Date spendAt = Date.valueOf(date);
			this.preparedStatement.setDate(4, spendAt);
			this.preparedStatement.setInt(5, expense.getExpenseId());
			this.preparedStatement.executeUpdate();
			AlertMaker.showAlert(AlertType.INFORMATION,"Successful Message", null, "Expenses are updated successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			AlertMaker.showAlert(AlertType.ERROR,"Error", "Error", "Record failed to update!");
			e.printStackTrace();
		}
	}
	
	public void setTargetExpense(int amount) {
		try {
			this.stmt = this.connection.createStatement();
			this.stmt.executeUpdate("UPDATE "+DBConst.USER_TABLE
									+" SET "+DBConst.TARGET_EXPENSE+"="+amount
									+" WHERE "+DBConst.USER_ID+" ='"+User.userId+"';");
			AlertMaker.showAlert(AlertType.INFORMATION,"Successful Message", null, "An Expense Target is set!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			AlertMaker.showAlert(AlertType.ERROR,"Error", "Error", "Setting expense target fail!");
			e.printStackTrace();
		}
	}
	
	public ObservableList<Expense> selectExpenseWith(String category) {
		ObservableList<Expense> expenseList = FXCollections.observableArrayList();
		try {
			this.stmt = this.connection.createStatement();
			this.rs = this.stmt.executeQuery("SELECT "+DBConst.SPEND_AT+","+DBConst.EXPENSE_NAME+","+DBConst.EXPENSE_AMOUNT
											+" FROM "+DBConst.EXPENSE_TABLE
											+" WHERE "+DBConst.EXPENSE_USER_ID+"='"+User.userId+"' and "+DBConst.EXPENSE_CATEGORY+" ='"+category+"';");
			while(this.rs.next()) {
				expenseList.add(new Expense(rs.getString(DBConst.SPEND_AT), rs.getString(DBConst.EXPENSE_NAME), rs.getInt(DBConst.EXPENSE_AMOUNT)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return expenseList;
	}
	
	public List<Expense> searchByCategory(String category){
		List<Expense> expenseList = new ArrayList<Expense>();
		try {
			this.stmt = this.connection.createStatement();
			this.rs = this.stmt.executeQuery("SELECT "+DBConst.EXPENSE_CATEGORY+", sum("+DBConst.EXPENSE_AMOUNT+") AS totalAmount "
											+" FROM "+DBConst.EXPENSE_TABLE
											+" WHERE userId='"+User.userId
											+"' and "+DBConst.EXPENSE_CATEGORY+" like '%"+category+"%'"
											+" GROUP BY "+DBConst.EXPENSE_CATEGORY+";");
			while(this.rs.next()) {
				expenseList.add(new Expense(rs.getString(DBConst.EXPENSE_CATEGORY), rs.getInt("totalAmount")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return expenseList;
	}
	
	public void withdrawAmount(Withdraw withdrawAmount) {
		String insertWithdraw = "INSERT INTO "+DBConst.WITHDRAW_TABLE+"("+DBConst.WITHDRAW_AMOUNT+", "+DBConst.WITHDRAW_AT+", "+DBConst.WITHDRAW_GOAL_ID+")"
				+"VALUES(?,?,?)";
		try {
		this.preparedStatement = DBConnection.getConnection().prepareStatement(insertWithdraw);
		
		this.preparedStatement.setString(1, String.valueOf(withdrawAmount.getWithdrawAmount()));
		this.preparedStatement.setString(2, String.valueOf(withdrawAmount.getWithdrawAt()));
		this.preparedStatement.setString(3, String.valueOf(withdrawAmount.getGoalId()));
		
		
		this.preparedStatement.executeUpdate();
		
		
		  Alert alert = new Alert(AlertType.NONE);
		  alert.setAlertType(AlertType.INFORMATION);
		  alert.setHeaderText("Successfully withdrawed!"); 
		  alert.show();
	 
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
	}
	
	public List<SaveAndWithdrawHistory> selectAllWithdrawData(int id ) {
		  System.out.println(id);
		  String sql = "SELECT * FROM withdraw where goalId = ?";
		  List<SaveAndWithdrawHistory> swhLists = new ArrayList<SaveAndWithdrawHistory>();
		  
		  try {
			  preparedStatement = this.connection.prepareStatement(sql);
			  preparedStatement.setInt(1, id);
				rs = preparedStatement.executeQuery();
				while (rs.next()) {
					swh = new SaveAndWithdrawHistory();
					swh.setAmount(rs.getDouble(DBConst.WITHDRAW_AMOUNT));
					swh.setAction("Withdraw");
					swh.setAtTime(rs.getString(DBConst.WITHDRAW_AT));
					
					swhLists.add(swh);

				}
				return swhLists;
			} catch (SQLException e) {
				e.printStackTrace();
				// AlertMaker.showErrorMessage("Error", "Goals loading Failed!");
				return null;
			}
			
		}
}
