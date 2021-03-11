package controller.expenseController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import alert.AlertMaker;
import database.ExpenseDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Expense;

public class HistoryController implements Initializable{

	@FXML
	private JFXComboBox<String> cobType;

	@FXML
	private TableView<Expense> tvHistory;

	@FXML
	private TableColumn<Expense, String> tcDate;

	@FXML
	private TableColumn<Expense, String> tcExpenseName;

	@FXML
	private TableColumn<Expense, String> tcCategory;

	@FXML
	private TableColumn<Expense, Integer> tcExpenseAmount;

	@FXML
	private DatePicker dpExpense;

	@FXML
	private ImageView ivSearch;

	@FXML
	private JFXComboBox<String> cobCategory;

	@FXML
	private JFXButton btnUpdate;

	private ExpenseDB expenseDB = ExpenseDB.getInstance();

	private Expense expense;

	@FXML
	void search(MouseEvent event) throws SQLException {
		clearData();
		if(dpExpense.isVisible()) {
			tvHistory.setItems(expenseDB.show("spendAt",dpExpense.getValue().toString()));
		}else {
			tvHistory.setItems(expenseDB.show("expenseCategory",cobCategory.getValue()));
		}
	}

	@FXML
	void showAll(ActionEvent event) throws SQLException {
		restore();
		clearData();
		getAllExpense();
	}

	private void getAllExpense() throws SQLException {
		tvHistory.setItems(expenseDB.show("all",null));
	}

	private void restore() {
		ivSearch.setVisible(false);
		cobCategory.setVisible(false);
		dpExpense.setVisible(false);
		cobType.setValue("");
	}

	private void clearData() {
		tvHistory.getItems().clear();
	}

	private void isCategoryType(boolean choice) {
		cobCategory.setVisible(choice);
		dpExpense.setVisible(!choice);
		ivSearch.setVisible(true);
	}

	@FXML
	void deleteExpense(ActionEvent event) throws SQLException {
		if(AlertMaker.showAlert(AlertType.CONFIRMATION, "Confirmation", "Delete Confirmation", "Do you want to delete selected record?")) {
			expense = tvHistory.getSelectionModel().getSelectedItem();
			expenseDB.deleteExpense(expense.getExpenseId());
			clearData();
			getAllExpense();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//set search type combo box
		ObservableList<String> type = FXCollections.observableArrayList("Category","Date");
		cobType.setItems(type);

		cobType.setOnAction(e -> {
			if(cobType.getValue() == "Category") {
				isCategoryType(true);
			}else if(cobType.getValue() == "Date"){
				isCategoryType(false);
			}
		});

		//set category combo box
		ObservableList<String> expenseCategory = FXCollections.observableArrayList();
		try {
			expenseCategory.addAll(expenseDB.selectCategory());
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		cobCategory.setItems(expenseCategory);

		//set table cell value
		tcDate.setCellValueFactory(new PropertyValueFactory<Expense, String>("spendAt"));
		tcExpenseName.setCellValueFactory(new PropertyValueFactory<Expense, String>("expenseName"));
		tcCategory.setCellValueFactory(new PropertyValueFactory<Expense, String>("expenseCategory"));
		tcExpenseAmount.setCellValueFactory(new PropertyValueFactory<Expense, Integer>("expenseAmount"));

		try {
			getAllExpense();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		btnUpdate.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						expense = tvHistory.getSelectionModel().getSelectedItem();
						if(expense != null) {
							// Call Update frame
							final Stage dialog = new Stage();
							dialog.initModality(Modality.APPLICATION_MODAL);
							Stage primaryStage = (Stage) btnUpdate.getScene().getWindow();
							dialog.initOwner(primaryStage);
							FXMLLoader fxmlLoader = new FXMLLoader();
							try {
								fxmlLoader.setLocation(getClass().getResource("../../view/expenseView/UpdateUI.fxml"));
								fxmlLoader.load();
								Parent root = fxmlLoader.getRoot();
								dialog.setScene(new Scene(root));
								UpdateController updateController = fxmlLoader.getController();
								updateController.initData(expense);
								dialog.showAndWait();
								clearData();
								try {
									getAllExpense();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else {
							AlertMaker.showAlert(AlertType.INFORMATION,"Information Message", null, "Please select a record which you want to update!");
						}
					}
				});
	}
}
