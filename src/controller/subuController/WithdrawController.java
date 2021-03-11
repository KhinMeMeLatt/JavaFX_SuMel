package controller.subuController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXTextField;

import database.GoalDBModel;
import database.SaveDBModel;
import database.WithdrawDBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.subuModel.SaveAndWithdrawHistory;
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
    double sum=0;
    double withdraw=0;
    double total;
   
    ObservableList<SaveAndWithdrawHistory> swhList = FXCollections.observableArrayList();
   
    SaveDBModel saveDBModel = new SaveDBModel();
	WithdrawDBModel withdrawDBModel = new WithdrawDBModel();
	HomeController newControl=new HomeController();

    @FXML
    void convertCurrency(ActionEvent event) {

    }

    @FXML
    void processAbout(ActionEvent event) {

    }

	
    void amount(int goalId) {
    	List<SaveAndWithdrawHistory> swhArrayList = new ArrayList<SaveAndWithdrawHistory>();	
    	List<SaveAndWithdrawHistory> swhArrayList2 = new ArrayList<SaveAndWithdrawHistory>();

		swhArrayList = saveDBModel.selectAllSaveData(goalId);
		swhArrayList2=withdrawDBModel.selectAllWithdrawData(goalId);
		
		
		for (SaveAndWithdrawHistory s : swhArrayList) {
			sum=sum+s.getValue();

		}
		System.out.println(sum);
		
		for (SaveAndWithdrawHistory s : swhArrayList2) {
			withdraw=withdraw+s.getValue();

		}
		System.out.println(withdraw);
		total=sum-withdraw;
		System.out.println(total);
		
		


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
			amount(GoalDBModel.goalId);
			java.util.Date date=new java.util.Date();
	    	java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());


	    	double withdrawValue =Double.valueOf(txtAmount.getText());
	    	if(withdrawValue>total) {
	    		lblValidation.setText("Sorrry,Your amount is not enough!");
	    	}
	    	else {
	    	Withdraw newWithdraw = new Withdraw(withdrawValue,sqlTime, GoalDBModel.goalId); 
	    	WithdrawDBModel withdrawModel = new WithdrawDBModel();
	    	withdrawModel.withdrawAmount(newWithdraw);
	    	}
			  } else {
				  System.out.println("canceled"); }
    	}
    	}
		catch(NumberFormatException e){
			lblValidation.setText("Please Enter Numbers!");
		}

    
    }
    
}
