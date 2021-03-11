package controller.expenseController;

import java.sql.SQLException;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import database.ExpenseDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.Expense;

public class UpdateController {
	
	@FXML
    private JFXTextField txtExpenseName;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private DatePicker dpSendAt;

    @FXML
    private JFXComboBox<String> cobCategory;
    
    @FXML
    private JFXButton btnUpdate;
    
    private ExpenseDB expenseDB = ExpenseDB.getInstance();
    
    private int expenseId;
    
    private void close() {
    	Stage oldStage = (Stage)btnUpdate.getScene().getWindow();//hide the current login windows
        oldStage.close();
    }
    
    @FXML
    void updateExpense(ActionEvent event) throws SQLException {
    	Expense expense = new Expense(expenseId,txtExpenseName.getText(), cobCategory.getValue(), Integer.valueOf(txtAmount.getText()),dpSendAt.getValue().toString());
    	expenseDB.updateExpense(expense);
    	close();
    }
    
    @FXML
    void processCancel(ActionEvent event) {
    	close();
    }

    public void initData(Expense expense) {
    	//set category combo box
    	ObservableList<String> expenseCategory = FXCollections.observableArrayList();
    	try {
    		expenseCategory.addAll(expenseDB.selectCategory());
    	} catch (SQLException e2) {
    		// TODO Auto-generated catch block
    		e2.printStackTrace();
    	}

    	cobCategory.setItems(expenseCategory);
    	
    	this.expenseId = expense.getExpenseId();
    	txtExpenseName.setText(expense.getExpenseName());
    	txtAmount.setText(String.valueOf(expense.getExpenseAmount()));
    	LocalDate date = LocalDate.parse(expense.getSpendAt());
    	dpSendAt.setValue(date);
    	cobCategory.setValue(expense.getExpenseCategory());
    }

}
