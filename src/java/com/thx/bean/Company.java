package com.thx.bean;

public class Company extends Pageable {

	private int id;
	private String name;
	private String fullName;
	private int type;
	private String nation;
	private String addr;
	private String contractName;
	private String contractAddr;
	private String contractInfo;
	private String remark;

	private double exportAmt;
	private double payedAmt;
	private double invoiceAmt;
	private double accAmt;

	private int onbussiness;
	private int retax;

	public double getExportAmt() {
		return exportAmt;
	}

	public void setExportAmt(double exportAmt) {
		this.exportAmt = exportAmt;
	}

	public double getPayedAmt() {
		return payedAmt;
	}

	public void setPayedAmt(double payedAmt) {
		this.payedAmt = payedAmt;
	}

	public double getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(double invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractAddr() {
		return contractAddr;
	}

	public void setContractAddr(String contractAddr) {
		this.contractAddr = contractAddr;
	}

	public String getContractInfo() {
		return contractInfo;
	}

	public void setContractInfo(String contractInfo) {
		this.contractInfo = contractInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public double getAccAmt() {
		return accAmt;
	}

	public void setAccAmt(double accAmt) {
		this.accAmt = accAmt;
	}

	public int compareTo(Company b) {
		return -this.getFullName().compareTo(b.getFullName());
	}

	public int getOnbussiness() {
		return onbussiness;
	}

	public void setOnbussiness(int onbussiness) {
		this.onbussiness = onbussiness;
	}

	public int getRetax() {
		return retax;
	}

	public void setRetax(int retax) {
		this.retax = retax;
	}

}
