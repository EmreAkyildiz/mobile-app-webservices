package com.example.mobileappws.io.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.example.mobileappws.shared.dto.CourseDto;

@Entity
@Table(name= "announcements")
public class AnnouncementEntity implements Serializable {
	private static final long serialVersionUID = -7826615064179470425L;
	
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(length = 30, nullable=false)
	private String announcementId;
	
	@Column(length = 120, nullable = false)
	private String content;
	
	//@Column(length=40,nullable = false)
	//private String createdDate;
	
	@ManyToOne
	@JoinColumn(name="courses_id")
	private CourseEntity courseDetails;
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	private UserEntity userDetails;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(String announcementId) {
		this.announcementId = announcementId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CourseEntity getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseEntity courseDetails) {
		this.courseDetails = courseDetails;
	}

	public UserEntity getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserEntity userDetails) {
		this.userDetails = userDetails;
	}

	//public String getCreatedDate() {
	//	return createdDate;
	//}

	//public void setCreatedDate(String createdDate) {
	//	this.createdDate = createdDate;
	//}
	
	
}
