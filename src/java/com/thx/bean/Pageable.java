package com.thx.bean;

import com.common.TxStringUtil;

public class Pageable {

	private int queryType = 0;
	private int startPage = 1;
	private int pageSize = 20;

	private String startTime;

	private String endTime;

	private int pageRange = 0;
	private int rowCount = 0; // 总行数
	private int pageCount = 0; // 总页数
	private int pageOffset = 0; // 当前页起始记录
	private int pageTail = 0; // 当前页到达的记录
	private String orderField; // 排序字段
	private String groupField; //
	private boolean orderDirection; //

	private String sortCriteria;// 排序条件
	private String sortOrder;// 排序方式

	// 页面显示分页按钮个数
	private int length = 6;

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getPageRange() {
		return pageRange;
	}

	public void setPageRange(int pageRange) {
		this.pageRange = pageRange;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getStartIndex() {
		return pageSize * (startPage - 1);
	}

	protected void doPage() {
		this.pageCount = this.rowCount / this.pageSize + 1;
		// 如果模板==0，且总数大于1，则减一
		if ((this.rowCount % this.pageSize == 0) && pageCount > 1)
			this.pageCount--;

		// 如果输入也页面编号（pageId）大于总页数，将pageId设置为pageCount;
		if (this.startPage > this.pageCount)
			this.startPage = this.pageCount;
		// this.pageOffset=(this.pageId-1)*this.pageSize+1;

		// this.pageTail=this.pageOffset+this.pageSize-1;

		// Mysql 算法
		this.pageOffset = (this.startPage - 1) * this.pageSize;
		this.pageTail = this.pageOffset + this.pageSize;
		if ((this.pageOffset + this.pageSize) > this.rowCount)
			this.pageTail = this.rowCount;
	}

	public String getOrderCondition() {
		String condition = "";
		String orderField = checkOrderFiled(this.orderField);
		if (orderField != null && orderField.length() != 0) {
			condition = " order by " + orderField + (orderDirection ? " " : " desc ");
		}
		return condition;
	}

	public String getPageQueryCondition() {
		pageOffset = (startPage - 1) * pageSize;
		String condition = " limit " + pageOffset + "," + pageSize;
		return condition;
	}

	public void setOrderDirection(boolean orderDirection) {
		this.orderDirection = orderDirection;
	}

	public boolean isOrderDirection() {
		return orderDirection;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}

	public int getPageOffset() {
		return pageOffset;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageTail(int pageTail) {
		this.pageTail = pageTail;
	}

	public int getPageTail() {
		return pageTail;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		this.doPage();
	}

	public int getRowCount() {
		return rowCount;
	}

	public String getGroupField() {
		String groupField = checkOrderFiled(this.groupField);
		return groupField;
	}

	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}

	public String getSortCriteria() {
		return sortCriteria;
	}

	public void setSortCriteria(String sortCriteria) {
		this.sortCriteria = sortCriteria;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String checkOrderFiled(String orderFiled) {

		if (TxStringUtil.isEmpty(orderFiled)) {
			return null;
		}

		String order = orderFiled.trim().toLowerCase();
		if (order.contains("select") || order.contains("update") || order.contains("delete") || order.contains("insert")
				|| order.contains("drop") || order.contains("and") || order.contains("or")) {
			return null;
		}
		return orderFiled;
	}

	public int getQueryType() {
		return queryType;
	}

	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}

}
