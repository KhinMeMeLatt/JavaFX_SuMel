package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.subuModel.SaveAndWithdrawHistory;
import model.subuModel.Withdraw;

public class WithdrawDBModel {

	private PreparedStatement preparedStatement;
	private SaveAndWithdrawHistory swh;
	private ResultSet rs;

	private static WithdrawDBModel withdrawDbModel = null;

	public static WithdrawDBModel getInstance() {
		if (withdrawDbModel == null) {
			withdrawDbModel = new WithdrawDBModel();
		}
		return withdrawDbModel;
	}

	public void withdrawAmount(Withdraw withdrawAmount) {
		String insertWithdraw = "INSERT INTO " + DBConst.WITHDRAW_TABLE + "(" + DBConst.WITHDRAW_AMOUNT + ", "
				+ DBConst.WITHDRAW_AT + ", " + DBConst.WITHDRAW_GOAL_ID + ")" + "VALUES(?,?,?)";
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
		} finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<SaveAndWithdrawHistory> selectAllWithdrawData(int id) {
		String sql = "SELECT * FROM withdraw where goalId = ?";
		List<SaveAndWithdrawHistory> swhLists = new ArrayList<SaveAndWithdrawHistory>();

		try {
			preparedStatement = DBConnection.getConnection().prepareStatement(sql);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				swh = new SaveAndWithdrawHistory();
				swh.setAmount(rs.getDouble(DBConst.WITHDRAW_AMOUNT));
				swh.setAction("Withdraw");
				swh.setAtTime(rs.getString(DBConst.WITHDRAW_AT));

				swhLists.add(swh);

			}
			return swhLists;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				DBConnection.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	public double selectAllWithdrawDataById(int id) {
		String sql = "SELECT withdrawAmount FROM withdraw where goalId = ?";
        double withDrawAmt = 0;
		try {
			preparedStatement = DBConnection.getConnection().prepareStatement(sql);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				withDrawAmt += rs.getDouble("withdrawAmount");
			}
			return withDrawAmt;
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
