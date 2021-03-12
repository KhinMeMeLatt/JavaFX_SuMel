package database;

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
import javafx.scene.control.Alert.AlertType;
import model.Expense;
import model.accountModel.User;

public class ExpenseDB {

	private PreparedStatement preparedStatement;
	private Statement stmt;
	private ResultSet rs;
	private String query;
	private static ExpenseDB expenseDb = null;
	
	public static ExpenseDB getInstance() {
        if (expenseDb == null) {
            expenseDb = new ExpenseDB();
        }
        return expenseDb;
    }
	
	public int insertExpense(Expense expense) {
		this.query = "INSERT INTO "+DBConst.EXPENSE_TABLE+"("+DBConst.EXPENSE_NAME+","
							+DBConst.EXPENSE_CATEGORY+","+DBConst.EXPENSE_AMOUNT+","+DBConst.SPEND_AT+","
							+DBConst.EXPENSE_USER_ID+")"
							+"VALUES(?,?,?,?,?)";
		try {
			this.preparedStatement = DBConnection.getConnection().prepareStatement(this.query);
			this.preparedStatement.setString(1, expense.getExpenseName());
			this.preparedStatement.setString(2, expense.getExpenseCategory());
			this.preparedStatement.setInt(3, expense.getExpenseAmount());
			
			LocalDate date = LocalDate.parse(expense.getSpendAt());
			Date spendAt = Date.valueOf(date.plus(1,ChronoUnit.DAYS));
			this.preparedStatement.setDate(4, spendAt);
			this.preparedStatement.setInt(5, User.userId);
			
			return this.preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
			return 0;
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	
	public void selectTargetExpense() throws SQLException {
		this.stmt = DBConnection.getConnection().createStatement();
		this.rs = this.stmt.executeQuery("SELECT "+DBConst.TARGET_EXPENSE+" FROM "+DBConst.USER_TABLE
										+" WHERE "+DBConst.USER_ID+"='"+User.userId+"'");
		this.rs.next();
		User.expectedExpense = this.rs.getInt(DBConst.TARGET_EXPENSE);
		DBConnection.getConnection().close();
	}
	
	private void selectCategoryAmount() throws SQLException {
		this.stmt = DBConnection.getConnection().createStatement();
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
		DBConnection.getConnection().close();
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
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return expenseList;
	}
	
	//History
	public ObservableList<Expense> show(String type, String value) throws SQLException{
		ObservableList<Expense> expenseList = FXCollections.observableArrayList();
		this.stmt = DBConnection.getConnection().createStatement();
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
		DBConnection.getConnection().close();
		return expenseList;
	}
	
	public ObservableList<String> selectCategory() throws SQLException{
		ObservableList<String> category = FXCollections.observableArrayList();
		this.stmt = DBConnection.getConnection().createStatement();
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
		DBConnection.getConnection().close();
		return category;
	}
	
	public void deleteExpense(int expenseId) {
		try {
			this.stmt = DBConnection.getConnection().createStatement();
			this.stmt.executeUpdate("DELETE FROM "+DBConst.EXPENSE_TABLE+" WHERE "+DBConst.EXPENSE_ID+" ='"+expenseId+"';");
			AlertMaker.showAlert(AlertType.INFORMATION,"Successful Message", null, "A record is deleted successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			AlertMaker.showAlert(AlertType.ERROR,"Error", "Error", "Record deletion process Failed!");
			e.printStackTrace();
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void updateExpense(Expense expense) {
		this.query = "UPDATE "+DBConst.EXPENSE_TABLE+" SET "+DBConst.EXPENSE_NAME+"=?,"
							+DBConst.EXPENSE_CATEGORY+"=?,"+DBConst.EXPENSE_AMOUNT+"=?,"+DBConst.SPEND_AT+"=?"
							+"WHERE "+DBConst.EXPENSE_ID+" =?";
		try {
			this.preparedStatement = DBConnection.getConnection().prepareStatement(this.query);
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
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setTargetExpense(int amount) {
		try {
			this.stmt = DBConnection.getConnection().createStatement();
			this.stmt.executeUpdate("UPDATE "+DBConst.USER_TABLE
									+" SET "+DBConst.TARGET_EXPENSE+"="+amount
									+" WHERE "+DBConst.USER_ID+" ='"+User.userId+"';");
			AlertMaker.showAlert(AlertType.INFORMATION,"Successful Message", null, "An Expense Target is set!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			AlertMaker.showAlert(AlertType.ERROR,"Error", "Error", "Setting expense target fail!");
			e.printStackTrace();
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ObservableList<Expense> selectExpenseWith(String category) {
		ObservableList<Expense> expenseList = FXCollections.observableArrayList();
		try {
			this.stmt = DBConnection.getConnection().createStatement();
			this.rs = this.stmt.executeQuery("SELECT "+DBConst.SPEND_AT+","+DBConst.EXPENSE_NAME+","+DBConst.EXPENSE_AMOUNT
											+" FROM "+DBConst.EXPENSE_TABLE
											+" WHERE "+DBConst.EXPENSE_USER_ID+"='"+User.userId+"' and "+DBConst.EXPENSE_CATEGORY+" ='"+category+"';");
			while(this.rs.next()) {
				expenseList.add(new Expense(rs.getString(DBConst.SPEND_AT), rs.getString(DBConst.EXPENSE_NAME), rs.getInt(DBConst.EXPENSE_AMOUNT)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return expenseList;
	}
	
	public List<Expense> searchByCategory(String category){
		List<Expense> expenseList = new ArrayList<Expense>();
		try {
			this.stmt = DBConnection.getConnection().createStatement();
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
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return expenseList;
	}
}