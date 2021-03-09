package controller.subuController;

import java.sql.SQLException;
import java.util.Optional;

import com.jfoenix.controls.JFXTextField;

import database.GoalDBModel;
import database.SaveDBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.subuModel.Save;



public class SaveController {

    @FXML
    private ImageView imgGoal;

    @FXML
    private Label lblGoalName;

    @FXML
    private JFXTextField txtAmount;
    @FXML
    private Label lblValidation;
    
   


    @FXML
    void convertCurrency(ActionEvent event) {

    }

    @FXML
    void processAbout(ActionEvent event) {

    }
    

    @FXML
    void processSave(ActionEvent event) throws SQLException {
    	try {
			double amount=Double.parseDouble(txtAmount.getText());
			lblValidation.setText(" ");
			if(txtAmount.getText().trim().isEmpty()) {
	    		
	    		txtAmount.setText("Please Enter Amount!");
	    		
	    	}
	    	
	    	else{
	    		
				
	    	Alert a = new Alert(AlertType.NONE); 
			
			
			a.setAlertType(AlertType.CONFIRMATION); 
			a.setHeaderText("Are you sure you want to save?");
			
			Optional<ButtonType> result = a.showAndWait();
			ButtonType button = result.orElse(ButtonType.CANCEL);

			if (button == ButtonType.OK) {
				java.util.Date date=new java.util.Date();
		    	java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());


		    	double saveValue =Double.valueOf(txtAmount.getText()); 
		    	Save newSave = new Save(saveValue,sqlTime, GoalDBModel.goalId); 
		    	SaveDBModel saveModel = new SaveDBModel();
		    	saveModel.saveAmount(newSave);		    	
				
				  } else {
					  System.out.println("canceled"); }
				 
			
	    	
			  
	    	}
			
		}
		catch(NumberFormatException e){
			lblValidation.setText("Please Enter Numbers!");
		}
    	
    	
    	
		
    	
    	

    }

	

	
   

}
