package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alert.AlertMaker;
import database.DBConnection;
import database.DBConst;
import model.Goal;

public class GoalDBModel {
	
	private PreparedStatement ps;
	private ResultSet rs;
	
	private Goal goal;
	
	public void insertGoal(Goal goal) {
		String insertGoal = "INSERT INTO " + DBConst.GOAL_TABLE + "(" + DBConst.GOAL_NAME + ", "
				+ DBConst.GOAL_IMAGE_PATH + ", " + DBConst.GOAL_AMOUNT + ", " + DBConst.START_DATE + ", "
				+ DBConst.END_DATE + ", " + DBConst.SAVE_TYPE + ", " + DBConst.AMOUNT_TO_SAVE + ", " + DBConst.IS_BREAK
				+ ", " + DBConst.GOAL_USER_ID + ")" + "VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			ps = DBConnection.getConnection().prepareStatement(insertGoal);

			ps.setString(1, goal.getGoalName());
			ps.setString(2, goal.getGoalImgPath());
			ps.setString(3, String.valueOf(goal.getGoalAmount()));
			ps.setString(4, goal.getStartDate());
			ps.setString(5, goal.getEndDate());
			ps.setString(6, goal.getSaveType());
			ps.setString(7, String.valueOf(goal.getAmountToSave()));
			ps.setString(8, String.valueOf(goal.getIsBreak()));
			ps.setString(9, String.valueOf(goal.getUserId()));

			ps.executeUpdate();
			AlertMaker.showSimpleAlert("Successful Message", "A new goal is created successfully!");
		} catch (SQLException e) {
			AlertMaker.showErrorMessage("Error", "Goal Creation Failed!");
		}
	}
	
	public List<Goal> selectAllGoal(int userID) {
		List<Goal> goalList = new ArrayList<Goal>();
		String selectAllGoal = "SELECT * FROM "+DBConst.GOAL_TABLE +" WHERE "+DBConst.USER_ID +" = "+ userID;
		try {
			ps = DBConnection.getConnection().prepareStatement(selectAllGoal);
			rs = ps.executeQuery();
			while(rs.next()) {
				goal = new Goal();
				goal.setGoalId(rs.getInt(DBConst.GOAL_ID));
				goal.setGoalName(rs.getString(DBConst.GOAL_NAME));
				goal.setGoalImgPath(rs.getString(DBConst.GOAL_IMAGE_PATH));
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
			ps = DBConnection.getConnection().prepareStatement(selectAllGoal);
			rs = ps.executeQuery();
			while(rs.next()) {
				goal.setGoalId(rs.getInt(DBConst.GOAL_ID));
				goal.setGoalImgPath(rs.getString(DBConst.GOAL_IMAGE_PATH));
				goal.setStartDate(rs.getString(DBConst.START_DATE));
				goal.setEndDate(rs.getString(DBConst.END_DATE));
				goal.setSaveType(rs.getString(DBConst.SAVE_TYPE));
				goal.setGoalAmount(rs.getInt(DBConst.TARGET_AMOUNT));
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
