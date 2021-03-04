package controller.subuController;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import database.GoalDBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.accountModel.User;
import model.subuModel.Goal;

public class JustSaveController implements Initializable{

	@FXML
	private JFXTextField txtGoalName;

	@FXML
    private ImageView imViewGoal;
	
	@FXML
    private JFXButton btnCreateGoal;
	
	@FXML
    private DatePicker dpStartDate;
	
	private Image goalImage;
    
    private String imageName;	

	@FXML
	void processBack(ActionEvent event) {

	}
	
	@FXML
	 void addImage(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
    	
    	//define initial directory
    	fileChooser.setInitialDirectory(new File("C:\\"));
    	
    	//define image extension
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.ico"));
    	File imgFile = fileChooser.showOpenDialog(null);
    	
    	if(imgFile != null) {
    		this.goalImage = new Image(imgFile.toURI().toString());
    		imViewGoal.setImage(this.goalImage);
    		this.imageName = imgFile.getName();
    	}else {
    		this.imageName = "JustSave.png";
    	}
    }

	@FXML
	void processCreateGoal(ActionEvent event) {
		String goalName = txtGoalName.getText();
    	Goal newGoal = new Goal(goalName, this.imageName, 0, dpStartDate.getValue().toString(), null, null, 0, false, User.userId);
    	GoalDBModel goalModel = new GoalDBModel();
    	goalModel.insertGoal(newGoal);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dpStartDate.setValue(LocalDate.now()); //Set current date in date picker for start date
		
		btnCreateGoal.disableProperty().bind((
				txtGoalName.textProperty().isNotEmpty()).not());
	}

}
