package model;

public class Subu {
	
	private String sbName;
	private int currentPrice;
	private String sbImageSrc;
	
	public Subu() {
		
	};
	
	public Subu(String sbName, int currentPrice, String sbImageSrc) {
		super();
		this.sbName = sbName;
		this.currentPrice = currentPrice;
		this.sbImageSrc = sbImageSrc;
	}
	
	public String getSbName() {
		return sbName;
	}
	public int getCurrentPrice() {
		return currentPrice;
	}
	public String getSbImageSrc() {
		return sbImageSrc;
	}
	public void setSbName(String sbName) {
		this.sbName = sbName;
	}
	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}
	public void setSbImageSrc(String sbImageSrc) {
		this.sbImageSrc = sbImageSrc;
	}
	
	
	


}
