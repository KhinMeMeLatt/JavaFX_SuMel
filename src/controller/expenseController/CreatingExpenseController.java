package controller.expenseController;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import alert.AlertMaker;
import database.ExpenseDB;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Expense;
import model.accountModel.User;

public class CreatingExpenseController implements Initializable{

    @FXML
    private TableView<Expense> tvExpense;

    @FXML
    private TableColumn<Expense, String> tcName;

    @FXML
    private TableColumn<Expense, String> tcCategory;

    @FXML
    private TableColumn<Expense, Integer> tcAmount;
    
    @FXML
    private DatePicker dpSpendAt;

    @FXML
    private JFXTextField txtExpenseName;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXComboBox<String> cobCategory;
    
    @FXML
    private JFXButton btnAdd;
    
    @FXML
    private Label lblTotalItems;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private Label lblExpectedAmount;

    @FXML
    private Label lblExcessAmount;
    
    @FXML
    private JFXTextField txtCategory;
    
    private boolean success = false;

    private ObservableList<Expense> expenseList = FXCollections.observableArrayList();
    
    private IntegerProperty totalItems = new SimpleIntegerProperty(0);
    
    private IntegerProperty totalAmount = new SimpleIntegerProperty(0);
    
    private ExpenseDB expenseDB = ExpenseDB.getInstance(); 
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
    void addRow(ActionEvent event) {
    	// get current position
        TablePosition pos = tvExpense.getFocusModel().getFocusedCell();

        // clear current selection
        tvExpense.getSelectionModel().clearSelection();

        // create new record and add it to the model
        int expenseAmount;
        
        expenseAmount = (txtAmount.getText() == "") ? 0 : Integer.valueOf(txtAmount.getText());
        String category = (cobCategory.getValue() == "Others")? txtCategory.getText() : cobCategory.getValue();
        Expense expense = new Expense(txtExpenseName.getText(),category, expenseAmount, dpSpendAt.getValue().toString());
        tvExpense.getItems().add(expense);
        clearData();

        // get last row
        int row = tvExpense.getItems().size() - 1;
        tvExpense.getSelectionModel().select( row, pos.getTableColumn());

        // scroll to new row
        tvExpense.scrollTo(expense);
        
        totalItems.set(tvExpense.getItems().size()); //set Total Items
        totalAmount.set(totalAmount.get()+expenseAmount);
    }
    
    private void clearData() {
    	txtExpenseName.clear();
    	cobCategory.setValue("");
    	txtAmount.clear();
    }

    @FXML
    void removeSelectedRows(ActionEvent event) {
    	int removeAmount = tvExpense.getSelectionModel().getSelectedItem().getExpenseAmount();
    	tvExpense.getItems().removeAll(tvExpense.getSelectionModel().getSelectedItems());

        // table selects by index, so we have to clear the selection or else items with that index would be selected 
        tvExpense.getSelectionModel().clearSelection();
        totalItems.set(totalItems.get()-1);
        
        totalAmount.set(totalAmount.get()-removeAmount);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
    void releaseKey(KeyEvent event) {
    	if( event.getCode() == KeyCode.N && event.isControlDown()) {

            // move focus & selection
            // we need to clear the current selection first or else the selection would be added to the current selection since we are in multi selection mode 
            TablePosition pos = tvExpense.getFocusModel().getFocusedCell();

            if (pos.getRow() == -1) {
                tvExpense.getSelectionModel().select(0);
            } 
            // add new row when we are at the last row
            else if (pos.getRow() == tvExpense.getItems().size() -1) {
                addRow(null);
            } 
            // select next row, but same column as the current selection
            else if (pos.getRow() < tvExpense.getItems().size() -1) {
                tvExpense.getSelectionModel().clearAndSelect( pos.getRow() + 1, pos.getTableColumn());
            }
        }
    }
    
    @FXML
    void saveExpense(ActionEvent event) throws SQLException {
    	
    	expenseList = tvExpense.getItems();
    	expenseList.forEach(expense -> {
    		if(expense.getExpenseName() != "" && expense.getExpenseCategory() != "" && expense.getExpenseAmount() != 0) {
				success = (expenseDB.insertExpense(expense) != 0)? true : false;
			}
    	});
    	if(success == true) {
    		AlertMaker.showAlert(AlertType.INFORMATION,"Successful Message", null, "Expenses are recorded successfully!");
    	}else {
    		AlertMaker.showAlert(AlertType.ERROR,"Error", "Error", "Expenses record process Failed!");
    	}
    	tvExpense.getItems().clear();
    }
    
    private void makeEditableTable() {
    	//reimplement the table cell as a text field
        tcName.setCellFactory(TextFieldTableCell.forTableColumn());
        tcName.setOnEditCommit(new EventHandler<CellEditEvent<Expense,String>>() {
			
			@Override
			public void handle(CellEditEvent<Expense, String> t) {
	            ((Expense) t.getTableView().getItems().get(
	                t.getTablePosition().getRow())
	                ).setExpenseName(t.getNewValue());
	        }
		});
        
        tcCategory.setCellFactory(TextFieldTableCell.forTableColumn());
        tcCategory.setOnEditCommit(new EventHandler<CellEditEvent<Expense,String>>() {
			
			@Override
			public void handle(CellEditEvent<Expense, String> t) {
	            ((Expense) t.getTableView().getItems().get(
	                t.getTablePosition().getRow())
	                ).setExpenseCategory(t.getNewValue());
	        }
		});
        
        tcAmount.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public Integer fromString(String string) {
                try {
                    return Integer.parseInt(string);
                } catch (Exception e) {
                    return 0;
                }
            }

            @Override
            public String toString(Integer val) {
                return Integer.toString(val);
            }
        }));
        
        tcAmount.setOnEditCommit(new EventHandler<CellEditEvent<Expense,Integer>>() {
			
			@Override
			public void handle(CellEditEvent<Expense, Integer> t) {
				totalAmount.set(totalAmount.get()-t.getOldValue());
				totalAmount.set(totalAmount.get()+t.getNewValue());
				
	            ((Expense) t.getTableView().getItems().get(
	                t.getTablePosition().getRow())
	                ).setExpenseAmount(t.getNewValue());
	        }
		});
    }
    
    private void setIDCol() {
    	// add row index column as 1st column
        TableColumn<Expense, Expense> indexCol = new TableColumn<Expense, Expense>("#");

        indexCol.setCellFactory(new Callback<TableColumn<Expense, Expense>, TableCell<Expense, Expense>>() {
            @Override 
            public TableCell<Expense, Expense> call(TableColumn<Expense, Expense> param) {
                return new TableCell<Expense, Expense>() {
                    @Override 
                    protected void updateItem(Expense item, boolean empty) {
                        super.updateItem(item, empty);

                        if (this.getTableRow() != null) {

                            int index = this.getTableRow().getIndex();

                            if( index < tvExpense.getItems().size()) {
                                int rowNum = index + 1;
                                setText( String.valueOf(rowNum));
                            } else {
                                setText("");
                            }

                        } else {
                            setText("");
                        }
                    }
                };
            }
        });

        tvExpense.getColumns().add( 0, indexCol); // number column is at index 0
    }
    
    public void checkRequiredField() {
    	btnAdd.disableProperty().bind((
				txtExpenseName.textProperty().isNotEmpty().and(
				txtAmount.textProperty().isNotEmpty())).not());
    }
    
	@Override
	public void initialize(URL url, ResourceBundle resource) {
		// Set combo box values
//		ObservableList<String> category = FXCollections.observableArrayList(
//				"Travel Expense","Food","Electricity bill","Clothes", "Others"
//				);
		ObservableList<String> expenseCategory = FXCollections.observableArrayList();
		try {
			expenseCategory.addAll(expenseDB.selectCategory());
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		cobCategory.setItems(expenseCategory);
		cobCategory.setOnAction(e -> {
			if(cobCategory.getValue() == "Others") {
				txtCategory.setVisible(true);
			}else {
				txtCategory.setVisible(false);
			}
		});
		
		dpSpendAt.setValue(LocalDate.now()); //Set current date in date picker for start date
		
		//Set Table cell value
		tcName.setCellValueFactory(new PropertyValueFactory<Expense, String>("expenseName"));
		tcCategory.setCellValueFactory(new PropertyValueFactory<Expense, String>("expenseCategory"));
		tcAmount.setCellValueFactory(new PropertyValueFactory<Expense, Integer>("expenseAmount"));
		
		// single cell selection mode
		tvExpense.getSelectionModel().setCellSelectionEnabled(true);
		
		this.setIDCol();
        
        this.makeEditableTable();

        // allow multi selection
        tvExpense.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        totalItems.addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observableValue, Object oldValue, Object newValue) {
				lblTotalItems.setText(String.valueOf(totalItems.get()));
			}
		});
        
        totalAmount.addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observableValue, Object oldValue, Object newValue) {
				lblTotalAmount.setText(String.valueOf(totalAmount.get()));
				if(totalAmount.get() > User.expectedExpense) {
					lblExcessAmount.setText(String.valueOf(totalAmount.get()-User.expectedExpense));
				}
			}
		}); 
        
        try {
			expenseDB.selectTargetExpense();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        lblExpectedAmount.setText(String.valueOf(User.expectedExpense));
        
        checkRequiredField();
	}

}
