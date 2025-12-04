package com.book.bean;

public class ObjTarget {

	public static final int TYPE_PERSON = 1;
	public static final int TYPE_EVENT = 2;
	public static final int TYPE_OTHERS = 3;

	private int id;
	private int type;
	private String name;

	private int addrId;
	private String address;
	private String detail;
	private String attrList;

	private String neturl;

	private int dynasty = 0;
	private String dynastyName;

	private int country = 0;
	private String countryName;

	private String alias;

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	private int bgtime = 3000;
	private int endtime = 3000;
	private int graphId = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEndtime() {
		return endtime;
	}

	public void setEndtime(int endtime) {
		this.endtime = endtime;
	}

	public int getBgtime() {
		return bgtime;
	}

	public void setBgtime(int bgtime) {
		this.bgtime = bgtime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		String str = name;

		if (detail != null) {
			str = str + "," + detail;
		}

		return str;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}

		if (obj instanceof ObjTarget) {
			return this.getId() == ((ObjTarget) obj).getId();
		} else {
			return false;
		}

	}

	public int getDynasty() {
		return dynasty;
	}

	public void setDynasty(int dynasty) {
		this.dynasty = dynasty;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNeturl() {
		return neturl;
	}

	public void setNeturl(String neturl) {
		this.neturl = neturl;
	}

	public int getGraphId() {
		return graphId;
	}

	public void setGraphId(int graphId) {
		this.graphId = graphId;
	}

	public String getDynastyName() {
		return dynastyName;
	}

	public void setDynastyName(String dynastyName) {
		this.dynastyName = dynastyName;
	}

	public int getAddrId() {
		return addrId;
	}

	public void setAddrId(int addrId) {
		this.addrId = addrId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAttrList() {
		return attrList;
	}

	public void setAttrList(String attrList) {
		this.attrList = attrList;
	}
}
