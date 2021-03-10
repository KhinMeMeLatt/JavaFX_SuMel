package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.subuModel.Save;
import model.subuModel.SaveAndWithdrawHistory;

public class SaveDBModel {

	private Connection connection;
	private PreparedStatement ps;
	private Statement st;
	private ResultSet rs;
	private SaveAndWithdrawHistory swh;
	
	public SaveDBModel() {
		this.connection = DBConnection.getConnection();
	}
	
	public List<SaveAndWithdrawHistory> selectAllSaveData(int id ) {
	  System.out.println(id);
	  String sql = "SELECT * FROM save where goalId = ?";
	  List<SaveAndWithdrawHistory> swhLists = new ArrayList<SaveAndWithdrawHistory>();
	  
	  try {
			ps = this.connection.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				swh = new SaveAndWithdrawHistory();
				swh.setAmount(rs.getDouble(DBConst.SAVE_AMOUNT));
				swh.setAction("Save");
				swh.setAtTime(rs.getString(DBConst.SAVED_AT));
				
				swhLists.add(swh);

			}
			return swhLists;
		} catch (SQLException e) {
			e.printStackTrace();
			// AlertMaker.showErrorMessage("Error", "Goals loading Failed!");
			return null;
		}
		
	}
	
	public void saveAmount(Save saveAmount) {
		String insertSave = "INSERT INTO "+DBConst.SAVE_TABLE+"("+DBConst.SAVE_AMOUNT+", "+DBConst.SAVED_AT+", "+DBConst.SAVE_GOAL_ID+")"
				+"VALUES(?,?,?)";
		try {
		ps = DBConnection.getConnection().prepareStatement(insertSave);
		
		ps.setString(1, String.valueOf(saveAmount.getSaveAmount()));
		ps.setString(2, String.valueOf(saveAmount.getSaveAt()));
		ps.setString(3, String.valueOf(saveAmount.getGoalId()));
		
		
		ps.executeUpdate();
		
		
		  Alert alert = new Alert(AlertType.NONE);
		  alert.setAlertType(AlertType.INFORMATION);
		  alert.setHeaderText("Successfully saved!"); alert.show();	
			 
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
	}
	
	
	
	
}
