package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import alert.AlertMaker;
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
		String insertGoal = "INSERT INTO " + DBConst.GOAL_TABLE + "(" + DBConst.GOAL_NAME + ", "
				+ DBConst.GOAL_IMAGE_NAME + ", " + DBConst.GOAL_AMOUNT + ", " + DBConst.START_DATE + ", "
				+ DBConst.END_DATE + ", " + DBConst.SAVE_TYPE + ", " + DBConst.AMOUNT_TO_SAVE + ", " + DBConst.IS_BREAK
				+ ", " + DBConst.GOAL_USER_ID + ")" + "VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			this.ps = this.connection.prepareStatement(insertGoal);

			this.ps.setString(1, goal.getGoalName());
			this.ps.setString(2, goal.getGoalImgName());
			this.ps.setInt(3, goal.getGoalAmount());

			LocalDate sdate = LocalDate.parse(goal.getStartDate());
			Date startDate = Date.valueOf(sdate);
			this.ps.setDate(4, startDate);
			LocalDate edate = LocalDate.parse(goal.getStartDate());
			Date endDate = Date.valueOf(edate);
			this.ps.setDate(5, endDate);
			this.ps.setString(6, goal.getSaveType());
			this.ps.setDouble(7, goal.getAmountToSave());
			this.ps.setBoolean(8, goal.getIsBreak());
			this.ps.setInt(9, User.userId);

			this.ps.executeUpdate();
			AlertMaker.showAlert(AlertType.INFORMATION,"Successful Message", null, "Expenses are recorded successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			AlertMaker.showAlert(AlertType.ERROR,"Error", "Error", "Expenses record process Failed!");
			e.printStackTrace();
		}
	}

	public List<Goal> selectAllSubu() {
		List<Goal> goalList = new ArrayList<Goal>();
		String selectAllGoal = "SELECT * FROM goal WHERE userId = " + User.userId;

		try {
			ps = this.connection.prepareStatement(selectAllGoal);
			rs = ps.executeQuery();
			while (rs.next()) {
				goal = new Goal();
				goal.setGoalId(rs.getInt(DBConst.GOAL_ID));
				goal.setGoalName(rs.getString(DBConst.GOAL_NAME));
				goal.setGoalImgName(rs.getString(DBConst.GOAL_IMAGE_NAME));
				goal.setStartDate(rs.getString(DBConst.START_DATE));
				goal.setEndDate(rs.getString(DBConst.END_DATE));
				goal.setSaveType(rs.getString(DBConst.SAVE_TYPE));
				goal.setGoalAmount(rs.getInt(DBConst.GOAL_AMOUNT));
				goal.setAmountToSave(rs.getInt(DBConst.AMOUNT_TO_SAVE));
				goal.setIsBreak(rs.getBoolean(DBConst.IS_BREAK));

				goalList.add(goal);

			}
			// AlertMaker.showSimpleAlert("Successful Message", "All Goals Loaded
			// Successfully!");
			return goalList;
		} catch (SQLException e) {
			e.printStackTrace();
			// AlertMaker.showErrorMessage("Error", "Goals loading Failed!");
			return null;
		}
	}

	public Goal selectSubuBySubuName(String sbName) {
		Goal goal = new Goal();
		String selectSubu = "SELECT * FROM goal Where goalName like ? ";
		try {
			ps = this.connection.prepareStatement(selectSubu);
			ps.setString(1, sbName);
			rs = ps.executeQuery();
			while (rs.next()) {
				goal.setGoalId(rs.getInt(DBConst.GOAL_ID));
				goal.setGoalName(rs.getString(DBConst.GOAL_NAME));
				goal.setGoalImgName(rs.getString(DBConst.GOAL_IMAGE_NAME));
				goal.setStartDate(rs.getString(DBConst.START_DATE));
				goal.setEndDate(rs.getString(DBConst.END_DATE));
				goal.setSaveType(rs.getString(DBConst.SAVE_TYPE));
				goal.setGoalAmount(rs.getInt(DBConst.GOAL_AMOUNT));
				goal.setAmountToSave(rs.getInt(DBConst.AMOUNT_TO_SAVE));
				goal.setIsBreak(rs.getBoolean(DBConst.IS_BREAK));
			}
			return goal;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public double getCurrentAmountByID(int goalId) {
		double currentAmt = 0;
		String selectAmount = "SELECT SUM(save.saveAmount), SUM(withdraw.withdrawAmount) " + "FROM save "
				+ "JOIN withdraw ON save.goalId=withdraw.goalId " + "Where save.goalId = ?";
		try {
			ps = this.connection.prepareStatement(selectAmount);
			ps.setInt(1, goalId);
			rs = ps.executeQuery();
			while (rs.next()) {
				double saveAmount = rs.getDouble(1);
				double withDrawAmount = rs.getDouble(2);

				currentAmt = saveAmount - withDrawAmount;
			}
			return currentAmt;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public boolean isSubuNameExists(String name) {
		try {
			String checkstmt = "SELECT COUNT(*) FROM goal WHERE goalName=?";
			ps = this.connection.prepareStatement(checkstmt);
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				System.out.println(count);
				return (count > 0);
			}
		} catch (SQLException ex) {
            ex.printStackTrace();
		}
		return false;
	}

	public boolean deleteSubuBySubuName(String sbName) {
		String selectSubu = "Delete FROM goal Where goalName like ? ";
		try {
			ps = this.connection.prepareStatement(selectSubu);
			ps.setString(1, sbName);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * public boolean updateTargetGoal(Goal newGoal) { try { Statement stmt =
	 * this.connection.createStatement(); String updateString = "UPDATE goal SET " +
	 * "goalName = '"+newGoal.getGoalName() +
	 * "',goalImgName = '"+newGoal.getGoalImgName() +
	 * "',goalAmount = "+newGoal.getGoalAmount() +
	 * ",startDate = '"+newGoal.getStartDate() +
	 * "',endDate = '"+newGoal.getEndDate() + "',saveType = '"+newGoal.getSaveType()
	 * + "',amountToSave = '"+newGoal.getAmountToSave() +
	 * "',isBreak = '"+newGoal.getIsBreak() +
	 * "'WHERE goalId = '"+newGoal.getGoalId()+"';"; return
	 * stmt.executeUpdate(updateString)>0; } catch (SQLException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); return false; } }
	 */

	public boolean updateTargetGoal(Goal newGoal) {
		String updateString = "Update goal SET " + "goalName = ?," + "goalImgName = ?," + "goalAmount = ?,"
				+ "startDate = ?," + "endDate = ?," + "saveType = ?," + "amountToSave = ?," + "isBreak = ?"
				+ " WHERE goalId = ?;";
		connection = DBConnection.getConnection();
		try {
			ps = connection.prepareStatement(updateString);
			ps.setString(1, newGoal.getGoalName());
			ps.setString(2, newGoal.getGoalImgName());
			ps.setInt(3, newGoal.getGoalAmount());

			LocalDate sdate = LocalDate.parse(newGoal.getStartDate());
			Date startDate = Date.valueOf(sdate);
			ps.setDate(4, startDate);

			if (newGoal.getEndDate() == null) {
				ps.setDate(5, null);
			} else {
				LocalDate edate = LocalDate.parse(newGoal.getEndDate());
				Date endDate = Date.valueOf(edate);
				ps.setDate(5, endDate);
			}

			ps.setString(6, newGoal.getSaveType());
			ps.setDouble(7, newGoal.getAmountToSave());
			ps.setBoolean(8, newGoal.getIsBreak());
			ps.setInt(9, newGoal.getGoalId());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

}
