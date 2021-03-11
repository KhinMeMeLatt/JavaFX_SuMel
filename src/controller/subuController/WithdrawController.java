package controller.subuController;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import database.GoalDBModel;
import database.SaveDBModel;
import database.WithdrawDBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.subuModel.SaveAndWithdrawHistory;
import model.subuModel.Withdraw;

public class WithdrawController implements Initializable {

	@FXML
	private ImageView imgGoal;

	@FXML
	private Label lblGoalName;

	@FXML
	private JFXTextField txtAmount;
	@FXML
	private Label lblValidation;
	double sum = 0;
	double withdraw = 0;
	double total;

	ObservableList<SaveAndWithdrawHistory> swhList = FXCollections.observableArrayList();

	private SaveDBModel saveDBModel;
	private WithdrawDBModel withdrawDBModel;

	void amount(int goalId) {
		double saveAmt = saveDBModel.selectAllWithdrawDataById(goalId);
		double withdrawAmt = withdrawDBModel.selectAllWithdrawDataById(goalId);
		total = saveAmt - withdrawAmt;
	}

	@FXML
	void processWithdraw(ActionEvent event) throws SQLException {
		try {
			lblValidation.setText(" ");
			if (txtAmount.getText().trim().isEmpty()) {

				txtAmount.setText("Please Enter Amount!");

			} else {

				Alert a = new Alert(AlertType.NONE);

				a.setAlertType(AlertType.CONFIRMATION);
				a.setHeaderText("Are you sure you want to withdraw?");

				Optional<ButtonType> result = a.showAndWait();
				ButtonType button = result.orElse(ButtonType.CANCEL);

				if (button == ButtonType.OK) {
					amount(GoalDBModel.goalId);
					java.util.Date date = new java.util.Date();
					java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());

					double withdrawValue = Double.valueOf(txtAmount.getText());
					if (withdrawValue > total) {
						lblValidation.setText("Sorrry,Your amount is not enough!");
					} else {
						Withdraw newWithdraw = new Withdraw(withdrawValue, sqlTime, GoalDBModel.goalId);
						WithdrawDBModel withdrawModel = new WithdrawDBModel();
						withdrawModel.withdrawAmount(newWithdraw);
					}
				} else {
					System.out.println("canceled");
				}
			}
		} catch (NumberFormatException e) {
			lblValidation.setText("Please Enter Numbers!");
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		saveDBModel = SaveDBModel.getInstance();
		withdrawDBModel = WithdrawDBModel.getInstance();
		lblGoalName.setText(GoalDBModel.goalName);
		Image image = new Image("/assets/goals/"+GoalDBModel.goalImg);
		imgGoal.setImage(image);
	}

}
