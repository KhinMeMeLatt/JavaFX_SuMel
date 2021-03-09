package model.subuModel;

public class Subu {
	private String sbName;
	private double currentPrice;
	private String sbImageSrc;
	
	public Subu() {
		
	};
	
	public Subu(String sbName, double currentPrice, String sbImageSrc) {
		super();
		this.sbName = sbName;
		this.currentPrice = currentPrice;
		this.sbImageSrc = sbImageSrc;
	}
	
	public String getSbName() {
		return sbName;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public String getSbImageSrc() {
		return sbImageSrc;
	}
	public void setSbName(String sbName) {
		this.sbName = sbName;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public void setSbImageSrc(String sbImageSrc) {
		this.sbImageSrc = sbImageSrc;
	}

	@Override
	public String toString() {
		return "Subu [sbName=" + sbName + ", currentPrice=" + currentPrice + ", sbImageSrc=" + sbImageSrc + "]";
	}
	
	

}
