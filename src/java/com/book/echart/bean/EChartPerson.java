package com.book.echart.bean;

public class EChartPerson {

	private String id = "";
	private String name = "";
	private String symbolSize = "12";
	private String category = "";
	private boolean draggable = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbolSize() {
		return symbolSize;
	}

	public void setSymbolSize(String symbolSize) {
		this.symbolSize = symbolSize;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof EChartPerson) {
			return (this.getId() == ((EChartPerson) obj).getId());
		} else {
			return false;
		}

	}

}
