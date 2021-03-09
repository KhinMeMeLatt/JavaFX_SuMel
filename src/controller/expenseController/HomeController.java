package controller.expenseController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;

import alert.AlertMaker;
import controller.CurrencyController;
import controller.accountController.AboutController;
import database.AccountDBModel;
import database.ExpenseDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Expense;
import model.accountModel.User;

public class HomeController implements Initializable{

	@FXML
    private JFXButton btnCurrency;

    @FXML
    private JFXButton btnAbout;
    
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
	private TableView<Expense> tvHistory;

	@FXML
	private TableColumn<Expense, String> tcDate;

	@FXML
	private TableColumn<Expense, String> tcName;

	@FXML
	private TableColumn<Expense, Integer> tcAmount;

	@FXML
	private JFXTextField txtSearch;
	
	@FXML
    private JFXButton btnExpense;

    @FXML
    private JFXButton btnHistory;

	public static ObservableList<Expense> expense;

	@FXML
	void setTargetExpense(ActionEvent event) throws SQLException {
		expenseDB.selectTargetExpense();
		targetExpense = AlertMaker.createTextDialog();
		if(targetExpense != -1 && targetExpense != User.expectedExpense) {
			expenseDB.setTargetExpense(targetExpense);
		}
	}

	private void clearGrid() {
		this.myExpense.getChildren().clear();
	}

	private void setExpense(List<Expense> expenseList) {
		clearGrid();
		int row = 1;
		int column = 0;
		for (Expense expense : expenseList) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../../view/expenseView/Category.fxml"));
			VBox expenseVBox;
			try {
				expenseVBox = loader.load();
				expenseVBox.setOnMouseClicked((e)->{
					setTableData(expense.getExpenseCategory());
				});
				CategoryController categoryController = loader.getController();
				categoryController.initData(expense);
				JFXDepthManager.setDepth(expenseVBox, 1);
				myExpense.add(expenseVBox, column++, row);
				GridPane.setMargin(expenseVBox, new Insets(10, 10, 5, 10));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void setTableData(String category) {
		tvHistory.setItems(expenseDB.selectExpenseWith(category));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//set User name
		try {
			AccountDBModel account = new AccountDBModel();
			lblUserName.setText(account.getUser());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Draw Pie chart
		try {
			pcExpense.setData(expenseDB.selectWithCategory());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//		//set table cell value
		tcDate.setCellValueFactory(new PropertyValueFactory<Expense, String>("spendAt"));
		tcName.setCellValueFactory(new PropertyValueFactory<Expense, String>("expenseName"));
		tcAmount.setCellValueFactory(new PropertyValueFactory<Expense, Integer>("expenseAmount"));

		txtSearch.textProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				String keyword = txtSearch.getText();
				expenseList = (keyword == "")? expenseDB.getCategoryAmount() :expenseDB.searchByCategory(keyword);
				setExpense(expenseList);
			}
		});

		JFXDepthManager.setDepth(history, 1);

		//set data in expense panel
		JFXDepthManager.setDepth(expenseScrollPane, 1);

		expenseList = expenseDB.getCategoryAmount();
		setExpense(expenseList);

		//expense note
		btnExpense.setOnAction(
				new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						final Stage dialog = new Stage();
						dialog.initModality(Modality.APPLICATION_MODAL);
						Stage primaryStage = (Stage) btnExpense.getScene().getWindow();
						dialog.initOwner(primaryStage);
						FXMLLoader fxmlLoader = new FXMLLoader();
						try {
							fxmlLoader.setLocation(getClass().getResource("../../view/expenseView/CreatingExpenseUI.fxml"));
							fxmlLoader.load();
							Parent root = fxmlLoader.getRoot();
							dialog.setScene(new Scene(root));
							CreatingExpenseController expenseController = fxmlLoader.getController();
							dialog.showAndWait();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		
		//history
		btnHistory.setOnAction(
				new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						final Stage dialog = new Stage();
						dialog.initModality(Modality.APPLICATION_MODAL);
						Stage primaryStage = (Stage) btnHistory.getScene().getWindow();
						dialog.initOwner(primaryStage);
						FXMLLoader fxmlLoader = new FXMLLoader();
						try {
							fxmlLoader.setLocation(getClass().getResource("../../view/expenseView/HistoryUI.fxml"));
							fxmlLoader.load();
							Parent root = fxmlLoader.getRoot();
							dialog.setScene(new Scene(root));
							HistoryController historyController = fxmlLoader.getController();
							dialog.showAndWait();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		//About
		btnAbout.setOnAction(
				new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						final Stage dialog = new Stage();
						dialog.initModality(Modality.APPLICATION_MODAL);
						Stage primaryStage = (Stage) btnAbout.getScene().getWindow();
						dialog.initOwner(primaryStage);
						FXMLLoader fxmlLoader = new FXMLLoader();
						try {
							fxmlLoader.setLocation(getClass().getResource("../../view/accountView/AboutUI.fxml"));
							fxmlLoader.load();
							Parent root = fxmlLoader.getRoot();
							dialog.setScene(new Scene(root));
							AboutController aboutController = fxmlLoader.getController();
							dialog.showAndWait();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		
		//Currency Converter
		btnCurrency.setOnAction(
				new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						final Stage dialog = new Stage();
						dialog.initModality(Modality.APPLICATION_MODAL);
						Stage primaryStage = (Stage) btnCurrency.getScene().getWindow();
						dialog.initOwner(primaryStage);
						FXMLLoader fxmlLoader = new FXMLLoader();
						try {
							fxmlLoader.setLocation(getClass().getResource("../../view/CurrencyConverterUI.fxml"));
							fxmlLoader.load();
							Parent root = fxmlLoader.getRoot();
							dialog.setScene(new Scene(root));
							CurrencyController currencyController = fxmlLoader.getController();
							dialog.showAndWait();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

}
