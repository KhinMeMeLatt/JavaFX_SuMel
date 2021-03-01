package controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Goal;
import model.GoalDBModel;

public class JustSaveController implements Initializable{

	@FXML
	private JFXTextField txtGoalName;

	@FXML
    private ImageView imViewGoal;
	
	@FXML
    private JFXButton btnCreateGoal;
	
	@FXML
    private DatePicker dpStartDate;
	
	private String imgSrc;

	@FXML
	void processBack(ActionEvent event) {

	}
	
	@FXML
    void addImage(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
    	
    	//define initial directory
    	fileChooser.setInitialDirectory(new File("C:\\"));
    	
    	//define image extension
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.ico"));
    	File imgFile = fileChooser.showOpenDialog(null);
    	
    	if(imgFile != null) {
    		this.imgSrc = imgFile.toURI().toString();
    		Image image = new Image(this.imgSrc);
    		imViewGoal.setImage(image);
    	}else {
    		this.imgSrc = "../assets/JustSave.png";
    	}
    }

	@FXML
	void processCreateGoal(ActionEvent event) {
		String goalName = txtGoalName.getText();
    	Goal newGoal = new Goal(goalName, this.imgSrc, 0, dpStartDate.getValue().toString(), null, null, 0, 0, 1);
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
