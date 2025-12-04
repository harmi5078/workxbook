package com.book.bean;

public class Paragraphs {

	private int bid;
	private int gid;
	private int linkTarId;
	private String content;
	private String transcation;
	private int albumId;
	private String chiefAlbum;
	private int volumeId;
	private String volume;
	private String bookName;
	private String condition;

	private boolean selected = false;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getChiefAlbum() {
		return chiefAlbum;
	}

	public void setChiefAlbum(String chiefAlbum) {
		this.chiefAlbum = chiefAlbum;
	}

	public int getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(int volumeId) {
		this.volumeId = volumeId;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof Paragraphs) {
			return this.getGid() == ((Paragraphs) obj).getGid();
		} else {
			return false;
		}
	}

	@Override
	public String toString() {

		if (content != null) {
			return content.trim().replace("\t", "");
		}

		return null;

	}

	public String getTranscation() {
		return transcation;
	}

	public void setTranscation(String transcation) {
		this.transcation = transcation;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLinkTarId() {
		return linkTarId;
	}

	public void setLinkTarId(int linkTarId) {
		this.linkTarId = linkTarId;
	}

}
