package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import alert.AlertMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import model.accountModel.User;
import model.subuModel.Goal;

public class GoalDBModel {

	public static int goalId;
	public static String goalName;
	public static String goalImg;

	private PreparedStatement ps;
	private ResultSet rs;
	private Goal goal;

	private static GoalDBModel goalDbModel = null;

	public static GoalDBModel getInstance() {
		if (goalDbModel == null) {
			goalDbModel = new GoalDBModel();
		}
		return goalDbModel;
	}

	public void insertGoal(Goal goal) {
		String insertGoal = "INSERT INTO " + DBConst.GOAL_TABLE + "(" + DBConst.GOAL_NAME + ", "
				+ DBConst.GOAL_IMAGE_NAME + ", " + DBConst.GOAL_AMOUNT + ", " + DBConst.START_DATE + ", "
				+ DBConst.END_DATE + ", " + DBConst.SAVE_TYPE + ", " + DBConst.AMOUNT_TO_SAVE + ", " + DBConst.IS_BREAK
				+ ", " + DBConst.GOAL_USER_ID + ")" + "VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			this.ps = DBConnection.getConnection().prepareStatement(insertGoal);

			this.ps.setString(1, goal.getGoalName());
			this.ps.setString(2, goal.getGoalImgName());
			this.ps.setInt(3, goal.getGoalAmount());

			LocalDate sdate = LocalDate.parse(goal.getStartDate());
			Date startDate = Date.valueOf(sdate.plus(1,ChronoUnit.DAYS));
			this.ps.setDate(4, startDate);
			LocalDate edate = LocalDate.parse(goal.getEndDate());
			Date endDate = Date.valueOf(edate.plus(1,ChronoUnit.DAYS));
			this.ps.setDate(5, endDate);
			this.ps.setString(6, goal.getSaveType());
			this.ps.setDouble(7, goal.getAmountToSave());
			this.ps.setBoolean(8, goal.getIsBreak());
			this.ps.setInt(9, User.userId);

			this.ps.executeUpdate();
			AlertMaker.showAlert(AlertType.INFORMATION, "Successful Message", null, "Goal created successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Expenses record process Failed!");
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

	public ObservableList<Goal> selectAllSubu() {
		ObservableList<Goal> goalList = FXCollections.observableArrayList();
		String selectAllGoal = "SELECT * FROM goal WHERE userId = " + User.userId;

		try {
			ps = DBConnection.getConnection().prepareStatement(selectAllGoal);
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
			return goalList;
		} catch (SQLException e) {
			e.printStackTrace();
			// AlertMaker.showErrorMessage("Error", "Goals loading Failed!");
			return null;
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ObservableList<Goal> searchSubuByName(String name) {
		ObservableList<Goal> goalList = FXCollections.observableArrayList();
		String selectAllGoal = "SELECT * FROM goal WHERE goalName like ? AND userId = " + User.userId;

		try {
			ps = DBConnection.getConnection().prepareStatement(selectAllGoal);
			ps.setString(1, "%" + name + "%");
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
			return goalList;
		} catch (SQLException e) {
			e.printStackTrace();
			// AlertMaker.showErrorMessage("Error", "Goals loading Failed!");
			return null;
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Goal selectSubuBySubuName(String sbName) {
		Goal goal = new Goal();
		String selectSubu = "SELECT * FROM goal Where goalName like ? AND userId = " + User.userId;
		try {
			ps = DBConnection.getConnection().prepareStatement(selectSubu);
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
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isSubuNameExists(String name) {
		try {
			String checkstmt = "SELECT COUNT(*) FROM goal WHERE goalName=? AND goalId != " + goalId + " AND userId = "
					+ User.userId;
			ps = DBConnection.getConnection().prepareStatement(checkstmt);
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count > 0);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean deleteSubuBySubuName(String sbName) {
		String selectSubu = "Delete FROM goal Where goalName like ? AND userId = " + User.userId;
		try {
			ps = DBConnection.getConnection().prepareStatement(selectSubu);
			ps.setString(1, sbName);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public boolean updateTargetGoal(Goal newGoal) {
		String updateString = "Update goal SET " + "goalName = ?," + "goalImgName = ?," + "goalAmount = ?,"
				+ "startDate = ?," + "endDate = ?," + "saveType = ?," + "amountToSave = ?," + "isBreak = ?"
				+ " WHERE goalId = ? AND userId = " + User.userId;
		try {
			ps = DBConnection.getConnection().prepareStatement(updateString);
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
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
