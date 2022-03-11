package com.example.mobileappws.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class AnnouncementDto implements Serializable {
	
	private static final long serialVersionUID = -6775460803632959353L;
	private long id;
	private String announcementId;
	private String content;
	private Date createdDate;
	
	private CourseDto courseDetails;
	private UserDto userDetails;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public CourseDto getCourseDetails() {
		return courseDetails;
	}
	public void setCourseDetails(CourseDto courseDetails) {
		this.courseDetails = courseDetails;
	}
	public String getAnnouncementId() {
		return announcementId;
	}
	public void setAnnouncementId(String announcementId) {
		this.announcementId = announcementId;
	}
	public UserDto getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDto userDetails) {
		this.userDetails = userDetails;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
