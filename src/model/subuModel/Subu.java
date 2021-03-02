package model.subuModel;

public class Subu {
	
	private String sbName;
	private int expectedAmount;
	private String startDate;
	private String endDate;
	private String saveType;
	private int amtToSave;
	private int currentPrice;
	private String sbImageSrc;
	
	public String getSbName() {
		return sbName;
	}
	public int getExpectedAmount() {
		return expectedAmount;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getSaveType() {
		return saveType;
	}
	public int getAmtToSave() {
		return amtToSave;
	}
	public String getSbImageSrc() {
		return sbImageSrc;
	}
	public void setSbName(String sbName) {
		this.sbName = sbName;
	}
	public void setExpectedAmount(int expectedAmount) {
		this.expectedAmount = expectedAmount;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	public void setAmtToSave(int amtToSave) {
		this.amtToSave = amtToSave;
	}
	public void setSbImageSrc(String sbImageSrc) {
		this.sbImageSrc = sbImageSrc;
	}
	public int getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	


}
