package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Expense {

	private SimpleIntegerProperty expenseId;
	private SimpleStringProperty expenseName;
	private SimpleStringProperty expenseCategory;
	private SimpleIntegerProperty expenseAmount;
	private SimpleStringProperty spendAt;
	
	public Expense(String expenseName, String expenseCategory, int expenseAmount, String spendAt) {
		this.expenseName = new SimpleStringProperty(expenseName);
		this.expenseCategory = new SimpleStringProperty(expenseCategory);
		this.expenseAmount = new SimpleIntegerProperty(expenseAmount); 
		this.spendAt = new SimpleStringProperty(spendAt);
	}
	
	public int getExpenseId() {
		return expenseId.get();
	}
	
	public String getExpenseName() {
		return expenseName.get();
	}
	
	public void setExpenseName(String expenseName) {
		this.expenseName.set(expenseName);
	}
	
	public String getExpenseCategory() {
		return expenseCategory.get();
	}
	
	public void setExpenseCategory(String expenseCategory) {
		this.expenseCategory.set(expenseCategory);
	}
	
	public int getExpenseAmount() {
		return expenseAmount.get();
	}
	
	public void setExpenseAmount(int expenseAmount) {
		this.expenseAmount.set(expenseAmount);
	}
	
	public String getSpendAt() {
		return spendAt.get();
	}
		
}
