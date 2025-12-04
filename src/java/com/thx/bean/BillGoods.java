package com.thx.bean;

public class BillGoods extends BillEntry {

	private int billId;
	private int gid;
	private int invoicId;
	private String goods;
	private String hscode;
	private double weight;
	private String units;
	private double billQuantity;
	private String billUnits;
	private double payAmt;
	private double billAmt;
	private double invoiceAmt;
	private double billPrice;
	private String taxAddr;
	private int type;
	private int status;

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getInvoicId() {
		return invoicId;
	}

	public void setInvoicId(int invoicId) {
		this.invoicId = invoicId;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getHscode() {
		return hscode;
	}

	public void setHscode(String hscode) {
		this.hscode = hscode;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public double getBillQuantity() {
		return billQuantity;
	}

	public void setBillQuantity(double billQuantity) {
		this.billQuantity = billQuantity;
	}

	public String getBillUnits() {
		return billUnits;
	}

	public void setBillUnits(String billUnits) {
		this.billUnits = billUnits;
	}

	public double getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(double payAmt) {
		this.payAmt = payAmt;
	}

	public double getBillAmt() {
		return billAmt;
	}

	public void setBillAmt(double billAmt) {
		this.billAmt = billAmt;
	}

	public double getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(double invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public String getTaxAddr() {
		return taxAddr;
	}

	public void setTaxAddr(String taxAddr) {
		this.taxAddr = taxAddr;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public double getBillPrice() {
		return billPrice;
	}

	public void setBillPrice(double billPrice) {
		this.billPrice = billPrice;
	}

}
