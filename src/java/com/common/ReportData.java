package com.common;

public class ReportData {

	private String key;
	private String name;
	private String unit;
	private double quantity;
	private int nums;
	private double amt;

	private double myiAmt;
	private double myoAmt;
	private double taiAmt;
	private double taoAmt;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getMyiAmt() {
		return myiAmt;
	}

	public void setMyiAmt(double myiAmt) {
		this.myiAmt = myiAmt;
	}

	public double getMyoAmt() {
		return myoAmt;
	}

	public void setMyoAmt(double myoAmt) {
		this.myoAmt = myoAmt;
	}

	public double getTaiAmt() {
		return taiAmt;
	}

	public void setTaiAmt(double taiAmt) {
		this.taiAmt = taiAmt;
	}

	public double getTaoAmt() {
		return taoAmt;
	}

	public void setTaoAmt(double taoAmt) {
		this.taoAmt = taoAmt;
	}

}
