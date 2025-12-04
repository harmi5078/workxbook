package com.book.bean;

public class Person {

	private int pid;
	private String name;
	private int nation;
	private String nationName;
	private String title;
	private String addr;
	private String detail;
	private int dynasty;

	private String dynastyName;

	private int postId;
	private String postName;
	private String postLevel;
	private String levelDetails;
	private String postDetails;

	private String neturl;

	private double latitude, longitude;

	public String getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
	}

	public String getPostDetails() {
		return postDetails;
	}

	public void setPostDetails(String postDetails) {
		this.postDetails = postDetails;
	}

	public int getNation() {
		return nation;
	}

	public void setNation(int nation) {
		this.nation = nation;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getDynasty() {
		return dynasty;
	}

	public void setDynasty(int dynasty) {
		this.dynasty = dynasty;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDynastyName() {
		return dynastyName;
	}

	public void setDynastyName(String dynastyName) {
		this.dynastyName = dynastyName;
	}

	public String toString() {
		String str = name;

		if (detail != null) {
			str = str + "," + detail;
		}

		return str;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getLevelDetails() {
		return levelDetails;
	}

	public void setLevelDetails(String levelDetails) {
		this.levelDetails = levelDetails;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getNeturl() {
		return neturl;
	}

	public void setNeturl(String neturl) {
		this.neturl = neturl;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
