package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.subuModel.Goal;

public class GoalDBModel {
	
	private PreparedStatement preparedStatement;

	Alert alert = new Alert(AlertType.NONE);
	
	public void insertGoal(Goal goal) {
		String insertGoal = "INSERT INTO "+DBConst.GOAL_TABLE+"("+DBConst.GOAL_NAME+", "+DBConst.GOAL_IMAGE_PATH+", "+DBConst.GOAL_AMOUNT+", "+DBConst.START_DATE+", "+DBConst.END_DATE+", "+DBConst.SAVE_TYPE+", "+DBConst.AMOUNT_TO_SAVE+", "+DBConst.IS_BREAK+", "+DBConst.GOAL_USER_ID+")"
				+"VALUES(?,?,?,?,?,?,?,?,?)";
		try {
		this.preparedStatement = DBConnection.getConnection().prepareStatement(insertGoal);
		
		this.preparedStatement.setString(1, goal.getGoalName());
		this.preparedStatement.setString(2, goal.getGoalImgName());
		this.preparedStatement.setInt(3, goal.getGoalAmount());
		
		LocalDate date = LocalDate.parse(goal.getStartDate());
		Date startDate = Date.valueOf(date);
		this.preparedStatement.setDate(4, startDate);
		this.preparedStatement.setString(5, goal.getEndDate());
		this.preparedStatement.setString(6, goal.getSaveType());
		this.preparedStatement.setDouble(7, goal.getAmountToSave());
		this.preparedStatement.setBoolean(8, goal.getIsBreak());
		this.preparedStatement.setInt(9, goal.getUserId());
		
		this.preparedStatement.executeUpdate();
		alert.setAlertType(AlertType.INFORMATION);
		alert.setTitle("Successful Message");
		alert.setHeaderText(null);
		alert.setContentText("A new goal is created successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Goal Creation Failed!");
			e.printStackTrace();
		} finally {
			alert.showAndWait();
		}
	}
}
