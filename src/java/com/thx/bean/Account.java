package com.thx.bean;

public class Account {

	private int accId;
	private int companyId;
	private String accName;
	private String accNo;
	private String bank;
	private int isCompany;
	private int status;
	private String remark;
	private String companyName;
	private String alias;
	private int isPayment;

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public int getIsCompany() {
		return isCompany;
	}

	public void setIsCompany(int isCompany) {
		this.isCompany = isCompany;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getIsPayment() {
		return isPayment;
	}

	public void setIsPayment(int isPayment) {
		this.isPayment = isPayment;
	}

	public String getAccDesc() {

		String desc = "";
		if (this.isCompany == 1) {
			desc = "公账|" + this.companyName;
		} else {
			desc = "私账|" + this.companyName + '(' + this.accName + ")";
		}

		return desc;
	}

}
