package controller.subuController;

import java.sql.SQLException;
import java.util.Optional;

import com.jfoenix.controls.JFXTextField;

import database.GoalDBModel;
import database.WithdrawDBModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.subuModel.Withdraw;

public class WithdrawController {

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
    void processWithdraw(ActionEvent event) throws SQLException{
    	try {
			double amount=Double.parseDouble(txtAmount.getText());
			lblValidation.setText(" ");
    	if(txtAmount.getText().trim().isEmpty()) {

    		txtAmount.setText("Please Enter Amount!");

    	}
    	else{
			
    	Alert a = new Alert(AlertType.NONE); 
		
		
		a.setAlertType(AlertType.CONFIRMATION); 
		a.setHeaderText("Are you sure you want to withdraw?");
		
		Optional<ButtonType> result = a.showAndWait();
		ButtonType button = result.orElse(ButtonType.CANCEL);

		if (button == ButtonType.OK) {
			java.util.Date date=new java.util.Date();
	    	java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());


	    	double withdrawValue =Double.valueOf(txtAmount.getText()); 
	    	Withdraw newWithdraw = new Withdraw(withdrawValue,sqlTime, GoalDBModel.goalId); 
	    	WithdrawDBModel withdrawModel = new WithdrawDBModel();
	    	withdrawModel.withdrawAmount(newWithdraw);
			
			  } else {
				  System.out.println("canceled"); }
    	}
    	}
		catch(NumberFormatException e){
			lblValidation.setText("Please Enter Numbers!");
		}

    
    }
    
}
