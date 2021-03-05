package model.subuModel;

import java.sql.Timestamp;

public class Withdraw {
	

	
	private double withdrawAmount;
	private Timestamp withdrawAt;
	private int goalId;
	
	
	public double getWithdrawAmount() {
		return withdrawAmount;
	}


	public void setWithdrawAmount(double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}


	public Timestamp getWithdrawAt() {
		return withdrawAt;
	}


	public void setWithdrawAt(Timestamp withdrawAt) {
		this.withdrawAt = withdrawAt;
	}


	public int getGoalId() {
		return goalId;
	}


	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}


	public Withdraw(Double withdrawAmount, Timestamp sqlTime, int goalId) {
		super();
		this.withdrawAmount = withdrawAmount;
		this.withdrawAt = sqlTime;
		this.goalId= goalId;
		
	}


}
