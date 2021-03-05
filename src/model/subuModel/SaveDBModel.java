package model.subuModel;

import java.beans.EventHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnection;
import database.DBConst;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class SaveDBModel {
	private PreparedStatement preparedStatement;

	
	
	public void saveAmount(Save saveAmount) {
		String insertSave = "INSERT INTO "+DBConst.SAVE_TABLE+"("+DBConst.SAVE_AMOUNT+", "+DBConst.SAVED_AT+", "+DBConst.SAVE_GOAL_ID+")"
				+"VALUES(?,?,?)";
		try {
		this.preparedStatement = DBConnection.getConnection().prepareStatement(insertSave);
		
		this.preparedStatement.setString(1, String.valueOf(saveAmount.getSaveAmount()));
		this.preparedStatement.setString(2, String.valueOf(saveAmount.getSaveAt()));
		this.preparedStatement.setString(3, String.valueOf(saveAmount.getGoalId()));
		
		
		this.preparedStatement.executeUpdate();
		
		
		  Alert alert = new Alert(AlertType.NONE);
		  alert.setAlertType(AlertType.INFORMATION);
		  alert.setHeaderText("Successfully saved!"); alert.show();
		 
		 


		
			 
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
	}

}
