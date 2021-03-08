package model.subuModel;

public class Goal {

	private int goalId;
	private String goalName;
	private String goalImgName;
	private int goalAmount;
	private String startDate;
	private String endDate;
	private String saveType;
	private double amountToSave;
	private boolean isBreak; // It is flag variable for money box status
	private int userId;
	
	public Goal() {
		
	};
	
	public Goal(int goalId,String goalName, String goalImgName, int goalAmount, String startDate, String endDate, String saveType, double amountToSave,
			boolean isBreak, int userId) {
		this.goalId = goalId;
		this.goalName = goalName;
		this.goalImgName = goalImgName;
		this.goalAmount = goalAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.saveType = saveType;
		this.amountToSave = amountToSave;
		this.isBreak = isBreak;
		this.userId = userId;
	}
	
	public Goal(String goalName, String goalImgName, int goalAmount, String startDate, String endDate, String saveType, double amountToSave,
			boolean isBreak, int userId) {
		this.goalName = goalName;
		this.goalImgName = goalImgName;
		this.goalAmount = goalAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.saveType = saveType;
		this.amountToSave = amountToSave;
		this.isBreak = isBreak;
		this.userId = userId;
	}
	
	public int getGoalId() {
		return goalId;
	}
	public void setGoalId(int goalId) {
		this.goalId = goalId;
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
	public boolean getIsBreak() {
		return isBreak;
	}
	public void setIsBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getGoalImgName() {
		return goalImgName;
	}

	public void setGoalImgName(String goalImgName) {
		this.goalImgName = goalImgName;
	}

	@Override
	public String toString() {
		return "Goal [goalId=" + goalId + ", goalName=" + goalName + ", goalImgName=" + goalImgName + ", goalAmount="
				+ goalAmount + ", startDate=" + startDate + ", endDate=" + endDate + ", saveType=" + saveType
				+ ", amountToSave=" + amountToSave + ", isBreak=" + isBreak + ", userId=" + userId + "]";
	}
}
