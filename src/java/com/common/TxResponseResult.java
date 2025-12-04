package com.common;

import java.util.HashMap;

import com.thx.bean.Pageable;

public class TxResponseResult {

	private String reCode;
	private String reMsg;
	private Object reObject;
	private Pageable pageable;
	private HashMap<String, Object> reportData = new HashMap<String, Object>();

	public static String CODE_SUCESS = "1";
	public static String CODE_FAILED = "2";

	public static TxResponseResult createSucessResponse(Object data) {
		TxResponseResult rs = new TxResponseResult();
		rs.setReObject(data);
		rs.setReCode(CODE_SUCESS);

		return rs;
	}

	public static TxResponseResult createFailedResponse(Object data) {
		TxResponseResult rs = new TxResponseResult();
		rs.setReObject(data);
		rs.setReCode(CODE_FAILED);

		return rs;
	}

	public static TxResponseResult createResponse(Object data) {
		TxResponseResult rs = new TxResponseResult();
		rs.setReObject(data);
		return rs;
	}

	public String getReCode() {
		return reCode;
	}

	public void setReCode(String reCode) {
		this.reCode = reCode;
	}

	public String getReMsg() {
		return reMsg;
	}

	public void setReMsg(String reMsg) {
		this.reMsg = reMsg;
	}

	public Object getReObject() {
		return reObject;
	}

	public void setReObject(Object reObject) {
		this.reObject = reObject;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public void addReportData(String name, double value) {
		reportData.put(name, value);
	}

	public HashMap<String, Object> getReportData() {
		return reportData;
	}

	public void setReportData(HashMap<String, Object> reportData) {
		this.reportData = reportData;
	}
}
