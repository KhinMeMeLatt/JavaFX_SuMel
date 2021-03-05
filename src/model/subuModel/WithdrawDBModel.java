package model.subuModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnection;
import database.DBConst;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class WithdrawDBModel {
private PreparedStatement preparedStatement;

	
	
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

}
