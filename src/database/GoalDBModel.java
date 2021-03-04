package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.accountModel.User;
import model.subuModel.Goal;

public class GoalDBModel {
	
	private Connection connection;
	private PreparedStatement ps;
	private ResultSet rs;
	private Goal goal;
	
	public GoalDBModel() {
		this.connection = DBConnection.getConnection();
	}

	public void insertGoal(Goal goal) {
		String insertGoal = "INSERT INTO "+DBConst.GOAL_TABLE+"("+DBConst.GOAL_NAME+", "
							+DBConst.GOAL_IMAGE_NAME+", "+DBConst.GOAL_AMOUNT+", "+DBConst.START_DATE
							+", "+DBConst.END_DATE+", "+DBConst.SAVE_TYPE+", "+DBConst.AMOUNT_TO_SAVE+", "
							+DBConst.IS_BREAK+", "+DBConst.GOAL_USER_ID+")"
							+"VALUES(?,?,?,?,?,?,?,?,?)";
		try {
		this.ps = this.connection.prepareStatement(insertGoal);
		
		this.ps.setString(1, goal.getGoalName());
		this.ps.setString(2, goal.getGoalImgName());
		this.ps.setInt(3, goal.getGoalAmount());
		
		LocalDate date = LocalDate.parse(goal.getStartDate());
		Date startDate = Date.valueOf(date);
		this.ps.setDate(4, startDate);
		this.ps.setString(5, goal.getEndDate());
		this.ps.setString(6, goal.getSaveType());
		this.ps.setDouble(7, goal.getAmountToSave());
		this.ps.setBoolean(8, goal.getIsBreak());
		this.ps.setInt(9, User.userId);
		
		this.ps.executeUpdate();
		AlertMaker.showSimpleAlert("Successful Message", "A new goal is created successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			AlertMaker.showErrorMessage("Error", "Goal Creation Failed!");
			e.printStackTrace();
		} 
	}
	
	public List<Goal> selectAllGoal(int userID) {
		List<Goal> goalList = new ArrayList<Goal>();
		String selectAllGoal = "SELECT * FROM "+DBConst.GOAL_TABLE +" WHERE "+DBConst.USER_ID +" = "+ userID;
		try {
			ps = this.connection.prepareStatement(selectAllGoal);
			rs = ps.executeQuery();
			while(rs.next()) {
				goal = new Goal();
				goal.setGoalId(rs.getInt(DBConst.GOAL_ID));
				goal.setGoalName(rs.getString(DBConst.GOAL_NAME));
				goal.setGoalImgName(rs.getString(DBConst.GOAL_IMAGE_NAME));
				goal.setStartDate(rs.getString(DBConst.START_DATE));
				goal.setEndDate(rs.getString(DBConst.END_DATE));
				goal.setSaveType(rs.getString(DBConst.SAVE_TYPE));
				goal.setGoalAmount(rs.getInt(DBConst.GOAL_AMOUNT));
				goal.setAmountToSave(rs.getInt(DBConst.AMOUNT_TO_SAVE));
				
				goalList.add(goal);
				
			}
		//	AlertMaker.showSimpleAlert("Successful Message", "All Goals Loaded Successfully!");
			return goalList;
		} catch (SQLException e) {
			e.printStackTrace();
		//	AlertMaker.showErrorMessage("Error", "Goals loading Failed!");
			return null;
		}
	}
	
	public List<Goal> selectGoalByName(String userID,String goalName) {
		List<Goal> goalList = new ArrayList<Goal>();
		String selectAllGoal = "SELECT * FROM " + DBConst.GOAL_TABLE + "Where" + DBConst.USER_ID + "=" + userID + "and"
				+ DBConst.GOAL_NAME + "=" + goalName;
		try {
			ps = this.connection.prepareStatement(selectAllGoal);
			rs = ps.executeQuery();
			while(rs.next()) {
				goal.setGoalId(rs.getInt(DBConst.GOAL_ID));
				goal.setGoalImgName(rs.getString(DBConst.GOAL_IMAGE_NAME));
				goal.setStartDate(rs.getString(DBConst.START_DATE));
				goal.setEndDate(rs.getString(DBConst.END_DATE));
				goal.setSaveType(rs.getString(DBConst.SAVE_TYPE));
				goal.setGoalAmount(rs.getInt(DBConst.GOAL_AMOUNT));
				goal.setAmountToSave(rs.getInt(DBConst.AMOUNT_TO_SAVE));
				
				goalList.add(goal);
			}
			AlertMaker.showSimpleAlert("Successful Message", "All Goals Loaded Successfully!");
			return goalList;
		} catch (SQLException e) {
			AlertMaker.showErrorMessage("Error", "Goals loading Failed!");
			return null;
		}
	}
}
