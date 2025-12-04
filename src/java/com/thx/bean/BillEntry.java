package com.thx.bean;

import java.util.ArrayList;

public class BillEntry extends Pageable {

	private int billId = 0;
	private int deliver = 0;
	private String deliverName;
	private String supplierName;
	private String entryDate;
	private String reqDate;
	private String shortDesc;
	private String billNo;
	private double deliverFee;

	private String descs;
	private String remark;
	private String createTime;
	private String truckDate;
	private int taxtype;
	private int supplier;
	private int container;
	private String goods;
	private double totalamt;
	private String billFile;

	private String loadPort;
	private String voyage;
	private String lading;
	private String amount;
	private String totalWeight;
	private String tripAgent;

	private ArrayList<BillGoods> billGoodses = new ArrayList<BillGoods>();

	public String getLoadPort() {
		return loadPort;
	}

	public void setLoadPort(String loadPort) {
		this.loadPort = loadPort;
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	public String getLading() {
		return lading;
	}

	public void setLading(String lading) {
		this.lading = lading;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTripAgent() {
		return tripAgent;
	}

	public void setTripAgent(String tripAgent) {
		this.tripAgent = tripAgent;
	}

	public String getTruckDate() {
		return truckDate;
	}

	public void setTruckDate(String truckDate) {
		this.truckDate = truckDate;
	}

	public int getTaxtype() {
		return taxtype;
	}

	public void setTaxtype(int taxtype) {
		this.taxtype = taxtype;
	}

	public int getContainer() {
		return container;
	}

	public int getSupplier() {
		return supplier;
	}

	public void setSupplier(int supplier) {
		this.supplier = supplier;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public double getTotalamt() {
		return totalamt;
	}

	public void setTotalamt(double totalamt) {
		this.totalamt = totalamt;
	}

	public void setContainer(int container) {
		this.container = container;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getDeliver() {
		return deliver;
	}

	public void setDeliver(int deliver) {
		this.deliver = deliver;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public double getDeliverFee() {
		return deliverFee;
	}

	public void setDeliverFee(double deliverFee) {
		this.deliverFee = deliverFee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getDeliverName() {
		return deliverName;
	}

	public void setDeliverName(String deliverName) {
		this.deliverName = deliverName;
	}

	public String getBillFile() {
		return billFile;
	}

	public void setBillFile(String billFile) {
		this.billFile = billFile;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public ArrayList<BillGoods> getBillGoodses() {
		return billGoodses;
	}

	public void setBillGoodses(ArrayList<BillGoods> billGoodses) {
		this.billGoodses = billGoodses;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}
}
