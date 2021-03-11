package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.subuModel.Save;
import model.subuModel.SaveAndWithdrawHistory;

public class SaveDBModel {

	private PreparedStatement ps;
	private ResultSet rs;
	private SaveAndWithdrawHistory swh;

	private static SaveDBModel saveDbModel = null;

	public static SaveDBModel getInstance() {
		if (saveDbModel == null) {
			saveDbModel = new SaveDBModel();
		}
		return saveDbModel;
	}

	public List<SaveAndWithdrawHistory> selectAllSaveData(int id) {
		String sql = "SELECT * FROM save where goalId = ?";
		List<SaveAndWithdrawHistory> swhLists = new ArrayList<SaveAndWithdrawHistory>();

		try {
			ps = DBConnection.getConnection().prepareStatement(sql);
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
		}finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void saveAmount(Save saveAmount) {
		String insertSave = "INSERT INTO " + DBConst.SAVE_TABLE + "(" + DBConst.SAVE_AMOUNT + ", " + DBConst.SAVED_AT
				+ ", " + DBConst.SAVE_GOAL_ID + ")" + "VALUES(?,?,?)";
		try {
			ps = DBConnection.getConnection().prepareStatement(insertSave);

			ps.setString(1, String.valueOf(saveAmount.getSaveAmount()));
			ps.setString(2, String.valueOf(saveAmount.getSaveAt()));
			ps.setString(3, String.valueOf(saveAmount.getGoalId()));

			ps.executeUpdate();

			Alert alert = new Alert(AlertType.NONE);
			alert.setAlertType(AlertType.INFORMATION);
			alert.setHeaderText("Successfully saved!");
			alert.show();

		} catch (SQLException e) {

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
	
	public double selectAllWithdrawDataById(int id) {
		String sql = "SELECT saveAmount FROM save where goalId = ?";
        double saveAmt = 0;
		try {
			ps = DBConnection.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				saveAmt += rs.getDouble("saveAmount");
			}
			return saveAmt;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
