package controller.expenseController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.effects.JFXDepthManager;

import alert.AlertMaker;
import database.ExpenseDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Expense;
import model.accountModel.User;

public class HomeController implements Initializable{

    @FXML
    private HBox history;

    @FXML
    private PieChart pcExpense;

    @FXML
    private HBox expenseScrollPane;

    @FXML
    private GridPane myExpense;

    @FXML
    private Label lblUserName;
    
    private int targetExpense;
    
    private ExpenseDB expenseDB = new ExpenseDB();
    
    private List<Expense> expenseList;

    @FXML
    void processExpense(ActionEvent event) {

    }

    @FXML
    void setTargetExpense(ActionEvent event) throws SQLException {
    	expenseDB.selectTargetExpense();
    	targetExpense = AlertMaker.createTextDialog();
    	if(targetExpense != -1 && targetExpense != User.expectedExpense) {
    		expenseDB.setTargetExpense(targetExpense);
    	}
    }

    @FXML
    void showHistory(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		JFXDepthManager.setDepth(history, 1);
		
		//Draw Pie chart
		try {
			pcExpense.setData(expenseDB.selectWithCategory());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//set data in expense panel
		JFXDepthManager.setDepth(expenseScrollPane, 1);
		int row = 1;
		int column = 0;
		try {
			expenseList = expenseDB.getCategoryAmount();
			
			for (Expense expense : expenseList) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../../view/expenseView/Category.fxml"));
				VBox expenseVBox = loader.load();
				CategoryController categoryController = loader.getController();
				categoryController.initData(expense);
				JFXDepthManager.setDepth(expenseVBox, 1);
				myExpense.add(expenseVBox, column++, row);
				GridPane.setMargin(expenseVBox, new Insets(10, 10, 5, 10));
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
