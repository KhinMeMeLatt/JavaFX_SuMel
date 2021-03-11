package model.subuModel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SaveAndWithdrawHistory {

	/*
	 * private SimpleStringProperty atTime; private SimpleStringProperty action;
	 * private SimpleDoubleProperty amount;
	 * 
	 * public SaveAndWithdrawHistory() {
	 * 
	 * }
	 * 
	 * public SaveAndWithdrawHistory(String atTime, String action, Double amount) {
	 * this.atTime = new SimpleStringProperty(atTime); this.action = new
	 * SimpleStringProperty(action); this.amount = new SimpleDoubleProperty(amount);
	 * 
	 * 
	 * } public String getAtTime() { return atTime.get(); } public String
	 * getAction() { return action.get(); } public Double getAmount() { return
	 * amount.get(); } public void setAtTime(String atTime) {
	 * this.atTime.set(atTime); } public void setAction(String action) {
	 * this.action.set(action); } public void setAmount(Double amount) {
	 * this.amount.set(0.0); }
	 */

	private String atTime;
	private String action;
	private double amount;
	
	public SaveAndWithdrawHistory() {
		
	}

	public SaveAndWithdrawHistory(String atTime, String action, double amount) {
		this.atTime = atTime;
		this.action = action;
		this.amount = amount;
	}

	public String getAtTime() {
		return atTime;
	}

	public String getAction() {
		return action;
	}

	public double getAmount() {
		return amount;
	}

	public void setAtTime(String atTime) {
		this.atTime = atTime;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "SaveAndWithdrawHistory [atTime=" + atTime + ", action=" + action + ", amount=" + amount + "]";
	}
	public double getValue() {
		return amount;
	}
	public void setValue(double amount) {
		this.amount = amount;
	}


}
