package com.book.bean;

public class TargetLink {

	private int targetId = 0;

	private int paragId = 0;

	private int status = 0;

	private String targetName;

	private String remark;

	public static int STATUS_EXSITS = 1;
	public static int STATUS_ADDED_FOR_TAR = 2;
	public static int STATUS_ADDED_FOR_NEWTAR = 3;

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getParagId() {
		return paragId;
	}

	public void setParagId(int paragId) {
		this.paragId = paragId;
	}

	@Override
	public String toString() {
		return "TargetLink [targetId=" + targetId + ", paragId=" + paragId + ", status=" + status + "]";
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
