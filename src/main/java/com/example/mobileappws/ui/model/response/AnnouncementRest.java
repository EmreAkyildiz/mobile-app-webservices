package com.example.mobileappws.ui.model.response;

import java.util.Date;

public class AnnouncementRest {
	private String content;
	private String announcementId;
	private String createdDate;

	public AnnouncementRest() {
		
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getAnnouncementId() {
		return announcementId;
	}
	public void setAnnouncementId(String announcementId) {
		this.announcementId = announcementId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	

}
