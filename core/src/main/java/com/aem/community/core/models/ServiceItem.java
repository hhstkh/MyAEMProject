package com.aem.community.core.models;

public class ServiceItem {
	
	private String title;
	private String avatarImgPath;
	private String description;
	private boolean isNewRow;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAvatarImgPath() {
		return avatarImgPath;
	}
	public void setAvatarImgPath(String avatarImgPath) {
		this.avatarImgPath = avatarImgPath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isNewRow() {
		return isNewRow;
	}
	public void setNewRow(boolean isNewRow) {
		this.isNewRow = isNewRow;
	}
	
}
