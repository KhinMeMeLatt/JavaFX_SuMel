package model;

public class Goal {

	private String goalName;
	private int goalAmount;
	private String startDate;
	private String endDate;
	private String saveType;
	private double amountToSave;
	private int isBreak; // It is flag variable for money box status
	private int userId;
	
	public Goal(String goalName, int goalAmount, String startDate, String endDate, String saveType, double amountToSave,
			int isBreak, int userId) {
		super();
		this.goalName = goalName;
		this.goalAmount = goalAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.saveType = saveType;
		this.amountToSave = amountToSave;
		this.isBreak = isBreak;
		this.userId = userId;
	}
	
	public String getGoalName() {
		return goalName;
	}
	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}
	public int getGoalAmount() {
		return goalAmount;
	}
	public void setGoalAmount(int goalAmount) {
		this.goalAmount = goalAmount;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	public double getAmountToSave() {
		return amountToSave;
	}
	public void setAmountToSave(double amountToSave) {
		this.amountToSave = amountToSave;
	}
	public int getIsBreak() {
		return isBreak;
	}
	public void setIsBreak(int isBreak) {
		this.isBreak = isBreak;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
