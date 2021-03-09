package controller.expenseController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Expense;

public class CategoryController {

    @FXML
    private VBox category;

    @FXML
    private Label lblcategoryName;

    @FXML
    private Label lblAmount;
    
    private String expenseCategory;

	public void initData(Expense expense) {
		expenseCategory = expense.getExpenseCategory();
		lblcategoryName.setText(expenseCategory);
		lblAmount.setText(String.valueOf(expense.getExpenseAmount()));
	}

}