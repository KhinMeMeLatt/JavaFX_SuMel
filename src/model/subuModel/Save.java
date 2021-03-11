package model.subuModel;

import java.sql.Timestamp;

public class Save {
	private double saveAmount;
	public Timestamp getSaveAt() {
		return saveAt;
	}
	public void setSaveAt(Timestamp saveAt) {
		this.saveAt = saveAt;
	}
	public int getGoalId() {
		return goalId;
	}
	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}

	private Timestamp saveAt;
	private int goalId;
	
	public double getSaveAmount() {
		return saveAmount;
	}
	public void setSaveAmount(double saveAmount) {
		this.saveAmount = saveAmount;
	}
	
	
	public Save(Double saveAmount, Timestamp sqlTime, int goalId) {
		super();
		this.saveAmount = saveAmount;
		this.saveAt = sqlTime;
		this.goalId= goalId;
		
	}
	

}
