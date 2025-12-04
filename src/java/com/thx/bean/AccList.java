package com.thx.bean;

import com.common.TxStringUtil;

public class AccList extends Pageable {

	private int tid;
	private int businessId;

	private String businessName;
	private String payCompany;
	private String payAccInfo;
	private String owerAccInfo;

	private String showPayInfo;
	private String showOwerInfo;

	private String payAlias;
	private String owerAlias;

	private String owerAccName;
	private String payAccName;

	private int payCompanyId;
	private int payAccount;
	private int owerAccount;
	private String payAmt;
	private String payTime;
	private int payType;
	private String remark;

	private int type;
	private int ioFlag;

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getPayAccInfo() {
		return payAccInfo;
	}

	public void setPayAccInfo(String payAccInfo) {
		this.payAccInfo = payAccInfo;
		this.setShowPayInfo(TxStringUtil.rightTrimStar(payAccInfo));
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public int getBusinessId() {
		return businessId;
	}

	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}

	public String getPayCompany() {
		return payCompany;
	}

	public void setPayCompany(String payCompany) {
		this.payCompany = payCompany;
	}

	public int getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(int payAccount) {
		this.payAccount = payAccount;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOwerAccInfo() {

		return owerAccInfo;
	}

	public void setOwerAccInfo(String owerAccInfo) {
		this.owerAccInfo = owerAccInfo;

		this.setShowOwerInfo(TxStringUtil.rightTrimStar(owerAccInfo));
	}

	public int getOwerAccount() {
		return owerAccount;
	}

	public void setOwerAccount(int owerAccount) {
		this.owerAccount = owerAccount;
	}

	public String getShowPayInfo() {
		return showPayInfo;
	}

	public void setShowPayInfo(String showPayInfo) {
		this.showPayInfo = showPayInfo;

	}

	public String getShowOwerInfo() {
		return showOwerInfo;
	}

	public void setShowOwerInfo(String showOwerInfo) {
		this.showOwerInfo = showOwerInfo;
	}

	public String getPayAccName() {
		return payAccName;
	}

	public void setPayAccName(String payAccName) {
		this.payAccName = payAccName;
	}

	public String getOwerAccName() {
		return owerAccName;
	}

	public void setOwerAccName(String owerAccName) {
		this.owerAccName = owerAccName;
	}

	public String getOwerAlias() {
		return owerAlias;
	}

	public void setOwerAlias(String owerAlias) {
		this.owerAlias = owerAlias;
	}

	public String getPayAlias() {
		return payAlias;
	}

	public void setPayAlias(String payAlias) {
		this.payAlias = payAlias;
	}

	public int getIoFlag() {
		return ioFlag;
	}

	public void setIoFlag(int ioFlag) {
		this.ioFlag = ioFlag;
	}

	public int getPayCompanyId() {
		return payCompanyId;
	}

	public void setPayCompanyId(int payCompanyId) {
		this.payCompanyId = payCompanyId;
	}

	public int compareTo(AccList b) {
		return this.payAmt.compareTo(b.getPayAmt());

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
