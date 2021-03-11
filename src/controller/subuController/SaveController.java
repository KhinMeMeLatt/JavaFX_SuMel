package controller.subuController;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import database.GoalDBModel;
import database.SaveDBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.subuModel.Save;

public class SaveController implements Initializable {

	@FXML
	private ImageView imgGoal;

	@FXML
	private Label lblGoalName;

	@FXML
	private JFXTextField txtAmount;
	@FXML
	private Label lblValidation;
	private SaveDBModel saveModel;

	@FXML
	void processSave(ActionEvent event) throws SQLException {
		try {
			lblValidation.setText(" ");
			if (txtAmount.getText().trim().isEmpty()) {
				txtAmount.setText("Please Enter Amount!");
			}

			else {

				Alert a = new Alert(AlertType.NONE);

				a.setAlertType(AlertType.CONFIRMATION);
				a.setHeaderText("Are you sure you want to save?");

				Optional<ButtonType> result = a.showAndWait();
				ButtonType button = result.orElse(ButtonType.CANCEL);

				if (button == ButtonType.OK) {
					Date date = new Date();
					Timestamp sqlTime = new Timestamp(date.getTime());

					double saveValue = Double.valueOf(txtAmount.getText());
					Save newSave = new Save(saveValue, sqlTime, GoalDBModel.goalId);
					saveModel.saveAmount(newSave);

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
		saveModel = SaveDBModel.getInstance();
		lblGoalName.setText(GoalDBModel.goalName);
		Image image = new Image("/assets/goals/"+GoalDBModel.goalImg);
		imgGoal.setImage(image);
	}

}
