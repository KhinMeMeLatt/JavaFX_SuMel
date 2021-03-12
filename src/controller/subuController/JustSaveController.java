package controller.subuController;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import alert.AlertMaker;
import database.GoalDBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.accountModel.User;
import model.subuModel.Goal;

public class JustSaveController implements Initializable {

	@FXML
	private JFXTextField txtGoalName;
	
	@FXML
        private Label nameLengthLbl;

	@FXML
	private ImageView imViewGoal;

	@FXML
	private JFXButton btnCreateGoal;

	@FXML
	private DatePicker dpStartDate;

	@FXML
	private Label nameExistLabel;

	private Image goalImage;

	private String imageName;

	private File imgFile;

	private Boolean isInEditMode = false;

	Goal justSaveSubu = new Goal();

	private GoalDBModel goalDbModel;

	@FXML
	void processBack(ActionEvent event) {
		Stage stage = (Stage) txtGoalName.getScene().getWindow();
		stage.close();
	}

	@FXML
	void nameExist(KeyEvent event) {
		if (goalDbModel.isSubuNameExists(txtGoalName.getText())) {
			nameExistLabel.setTextFill(Color.RED);
			nameExistLabel.setText("***Subu name is already exist!!!Please Enter Different name.");
		} else {
			nameExistLabel.setTextFill(Color.WHITE);
			nameExistLabel.setText("");
		}


		if(txtGoalName.getText().length() > 10) {
			nameLengthLbl.setTextFill(Color.RED);
			nameLengthLbl.setText("***Subu name length must not be greater than 10.");
		}else {
			nameLengthLbl.setTextFill(Color.WHITE);
			nameLengthLbl.setText("");
		}
	}

	@FXML
	void addImage(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();

		// define initial directory
		fileChooser.setInitialDirectory(new File("C:\\"));

		// define image extension
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.ico"));
		imgFile = fileChooser.showOpenDialog(null);

		if (imgFile != null) {
			this.goalImage = new Image(imgFile.toURI().toString());
			imViewGoal.setImage(this.goalImage);
		} else {
			imgFile = new File("src/assets/goals/JustSave.png");
		}
	}

	@FXML
	void processCreateGoal(ActionEvent event) {
		if (isInEditMode) {
			handleUpdateUntargetGoal();
			return;
		}

		String goalName = txtGoalName.getText();

		writeImage();

		if (!goalDbModel.isSubuNameExists(goalName)) {
			if (imageName == null) {
				imageName = "JustSave.png";
			} else {
				imageName = imgFile.getName();
			}
			Goal newGoal = new Goal(goalName, this.imageName, 0, dpStartDate.getValue().toString(), null, null, 0,
					false, User.userId);

			if (!(goalName.length() > 10)) {

				goalDbModel.insertGoal(newGoal);

			} else {
				AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Subu Name length must not be larger than 10.");
			}

		} else {
			AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Please Enter Different Subu name!");
		}
	}

	public void updateUntargetGoalUI(Goal goal) {

		justSaveSubu.setGoalId(goal.getGoalId());
		txtGoalName.setText(goal.getGoalName());
		Image image = null;
		if (goal.getGoalImgName() == null) {
			image = new Image(getClass().getResourceAsStream("../../assets/JustSave.png"));
		} else {
			try {
				image = new Image(new FileInputStream("src/assets/goals/" + goal.getGoalImgName()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		imageName = goal.getGoalImgName();
		imViewGoal.setImage(image);
		imgFile = new File("src/assets/goals/" + imageName);
		dpStartDate.setValue(null);

		String start = goal.getStartDate();
		LocalDate sDate = LocalDate.parse(start.substring(0, start.indexOf(" ")));
		dpStartDate.setValue(sDate);

		btnCreateGoal.setText("Update Goal");
		isInEditMode = true;
	}

	private void writeImage() {
		BufferedImage bImage;
		try {
			if (imgFile == null) {
				imgFile = new File("src/assets/JustSave.png");
				imageName = "JustSave.png";
			}
			bImage = ImageIO.read(imgFile);
			String mimeType = URLConnection.guessContentTypeFromName(imageName);
			ImageIO.write(bImage, mimeType.substring(mimeType.indexOf("/") + 1),
					new File("src/assets/goals/" + imageName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handleUpdateUntargetGoal() {

		String goalName = txtGoalName.getText();

		writeImage();

		Goal newGoal = new Goal(justSaveSubu.getGoalId(), goalName, this.imageName, 0,
				dpStartDate.getValue().toString(), null, null, 0, false, User.userId);

		if (!(goalName.length() > 10)) {
			if (!goalDbModel.isSubuNameExists(goalName)) {

				if (goalDbModel.updateTargetGoal(newGoal)) {
					AlertMaker.showAlert(AlertType.INFORMATION, "Successful Message", null,
							"Just Save Goal is updated successfully!");
				} else {
					AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Goal Update Failed!");
				}
			} else {
				AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Please Enter Different Subu name!");
			}

		} else {
			AlertMaker.showAlert(AlertType.ERROR, "Error", "Error", "Subu Name length must not be larger than 10.");
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		goalDbModel = GoalDBModel.getInstance();
		dpStartDate.setValue(LocalDate.now()); // Set current date in date picker for start date

		btnCreateGoal.disableProperty().bind((txtGoalName.textProperty().isNotEmpty()).not());
	}

}
