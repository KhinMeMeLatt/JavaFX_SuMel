package controller.expenseController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.effects.JFXDepthManager;

import database.ExpenseDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class HomeController implements Initializable{

    @FXML
    private HBox history;

    @FXML
    private PieChart pcExpense;

    @FXML
    private HBox subuScrollPane;

    @FXML
    private GridPane mySubus;

    @FXML
    private Label lblUserName;
    
    private ExpenseDB expenseDB = new ExpenseDB();

    @FXML
    void processExpense(ActionEvent event) {

    }

    @FXML
    void setTargetExpense(ActionEvent event) {

    }

    @FXML
    void showHistory(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		JFXDepthManager.setDepth(history, 1);
		try {
			pcExpense.setData(expenseDB.selectWithCategory());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
