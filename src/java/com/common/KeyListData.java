package com.common;

import java.util.ArrayList;

public class KeyListData {

	private String key;
	private ArrayList<Object> list = new ArrayList<Object>();
	private ReportData reportData = new ReportData();
	private Object object;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ArrayList<Object> getList() {
		return list;
	}

	public void setList(ArrayList<Object> list) {
		this.list = list;
	}

	public int compareTo(KeyListData b) {
		return -this.key.compareTo(b.getKey());

	}

	public ReportData getReportData() {
		return reportData;
	}

	public void setReportData(ReportData reportData) {
		this.reportData = reportData;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
