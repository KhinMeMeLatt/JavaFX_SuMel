package model.subuModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnection;
import database.DBConst;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GoalDBModel {
	
	private PreparedStatement preparedStatement;

	Alert alert = new Alert(AlertType.NONE);
	
	public void insertGoal(Goal goal) {
		String insertGoal = "INSERT INTO "+DBConst.GOAL_TABLE+"("+DBConst.GOAL_NAME+", "+DBConst.GOAL_IMAGE_PATH+", "+DBConst.GOAL_AMOUNT+", "+DBConst.START_DATE+", "+DBConst.END_DATE+", "+DBConst.SAVE_TYPE+", "+DBConst.AMOUNT_TO_SAVE+", "+DBConst.IS_BREAK+", "+DBConst.GOAL_USER_ID+")"
				+"VALUES(?,?,?,?,?,?,?,?,?)";
		try {
		this.preparedStatement = DBConnection.getConnection().prepareStatement(insertGoal);
		
		this.preparedStatement.setString(1, goal.getGoalName());
		this.preparedStatement.setString(2, goal.getGoalImgPath());
		this.preparedStatement.setString(3, String.valueOf(goal.getGoalAmount()));
		this.preparedStatement.setString(4, goal.getStartDate());
		this.preparedStatement.setString(5, goal.getEndDate());
		this.preparedStatement.setString(6, goal.getSaveType());
		this.preparedStatement.setString(7, String.valueOf(goal.getAmountToSave()));
		this.preparedStatement.setString(8, String.valueOf(goal.getIsBreak()));
		this.preparedStatement.setString(9, String.valueOf(goal.getUserId()));
		
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
