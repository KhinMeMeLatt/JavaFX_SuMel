package controller.expenseController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Expense;

public class CategoryController {

    @FXML
    private VBox category;

    @FXML
    private Label lblcategoryName;

    @FXML
    private Label lblAmount;

    @FXML
    void showHistory(MouseEvent event) {

    }

	public void initData(Expense expense) {
		lblcategoryName.setText(expense.getExpenseCategory());
		lblAmount.setText(String.valueOf(expense.getExpenseAmount()));
	}

}