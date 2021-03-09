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

	GoalDBModel goalModel = new GoalDBModel();

	@FXML
	void processBack(ActionEvent event) {
		Stage stage = (Stage) txtGoalName.getScene().getWindow();
		stage.close();

	}

	@FXML
	void nameExist(KeyEvent event) {
      if(goalModel.isSubuNameExists(txtGoalName.getText())) {
    	  nameExistLabel.setTextFill(Color.RED);
    	  nameExistLabel.setText("***Subu name is already exist!!!Please Enter Different name.");
      }else {
    	  nameExistLabel.setTextFill(Color.WHITE);
    	  nameExistLabel.setText("");
      }
	}
	
	@FXML
	void addImage(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();

		// define initial directory
		fileChooser.setInitialDirectory(new File("C:\\"));

		// define image extension
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.ico"));
		File imgFile = fileChooser.showOpenDialog(null);

		if (imgFile != null) {
			this.goalImage = new Image(imgFile.toURI().toString());
			imViewGoal.setImage(this.goalImage);
			this.imageName = imgFile.getName();
		} else {
			this.imageName = "JustSave.png";
		}
	}

	@FXML
	void processCreateGoal(ActionEvent event) {
		if (isInEditMode) {
			handleUpdateUntargetGoal();
			return;
		}
		String goalName = txtGoalName.getText();
		Goal newGoal = new Goal(goalName, this.imageName, 0, dpStartDate.getValue().toString(), null, null, 0, false,
				User.userId);
		
		
		if(!goalModel.isSubuNameExists(goalName)) {
			goalModel.insertGoal(newGoal);
		}else {
			AlertMaker.showErrorMessage("Error", "Please Enter Different Subu name!");
		}
	}

	public void updateUntargetGoalUI(Goal goal) {

		justSaveSubu.setGoalId(goal.getGoalId());
		txtGoalName.setText(goal.getGoalName());
		Image image = null;
		if (goal.getGoalImgName() == null) {
			image = new Image(getClass().getResourceAsStream("../assets/img/JustSave.png"));
		} else {
			try {
				image = new Image(new FileInputStream("src/assets/img/" + goal.getGoalImgName()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		imageName = goal.getGoalImgName();
		imViewGoal.setImage(image);

		dpStartDate.setValue(null);

		String start = goal.getStartDate();
		LocalDate sDate = LocalDate.parse(start.substring(0, start.indexOf(" ")));
		dpStartDate.setValue(sDate);

		btnCreateGoal.setText("Update Goal");
		isInEditMode = true;
	}

	private void handleUpdateUntargetGoal() {

		String goalName = txtGoalName.getText();

		BufferedImage bImage;
		try {

			if (imgFile == null) {
				imgFile = new File("src/assets/goals/JustSave.png");
			}

			bImage = ImageIO.read(imgFile);
			String mimeType = URLConnection.guessContentTypeFromName(imageName);

			ImageIO.write(bImage, mimeType.substring(mimeType.indexOf("/") + 1),
					new File("src/assets/goals/" + imageName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(justSaveSubu.getGoalId());
		System.out.println(this.imageName);
		System.out.println(dpStartDate.getValue().toString());
		Goal newGoal = new Goal(justSaveSubu.getGoalId(), goalName, this.imageName, 0,
				dpStartDate.getValue().toString(), null, null, 0, false, User.userId);
			
		if(!goalModel.isSubuNameExists(goalName)) {
			if (goalModel.updateTargetGoal(newGoal)) {
				AlertMaker.showSimpleAlert("Successful Message", "Goal is updated successfully!");
			} else {
				AlertMaker.showErrorMessage("Error", "Goal Update Failed!");
			}
		}else {
			AlertMaker.showErrorMessage("Error", "Please Enter Different Subu name!");
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dpStartDate.setValue(LocalDate.now()); // Set current date in date picker for start date

		btnCreateGoal.disableProperty().bind((txtGoalName.textProperty().isNotEmpty()).not());
	}

}
