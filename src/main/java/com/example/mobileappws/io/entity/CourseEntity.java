package com.example.mobileappws.io.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class CourseEntity implements Serializable {

	private static final long serialVersionUID = 2001288192065187261L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false)
	private String courseId;
	
	@Column(nullable=false,length = 50,unique = true)
	private String courseName;
	
	@Column(nullable=false)
	private int aktsCredit;
	
	@Column(nullable=false)
	private int section;
	
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="courses_timeslots", 
			joinColumns =  @JoinColumn(name="course_id",nullable = false, updatable = true,referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(name="timeslot_id",nullable = false, updatable = true,referencedColumnName="id") )
	private Collection<TimeSlotEntity> timeslots;
	
		
	//course is the owner of the users, so we have to update it from courses
	@ManyToMany( )
	@JoinTable(name="users_courses", 
			joinColumns =  @JoinColumn(name="course_id",nullable = false, updatable = true,referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(name="user_id",nullable = false, updatable = true,referencedColumnName="id") )
	private Collection<UserEntity> users;
	
	@OneToMany(mappedBy = "courseDetails",cascade= CascadeType.PERSIST)
	private Collection<AnnouncementEntity> announcements;
	
	@OneToMany(mappedBy="courseDetails",cascade = {CascadeType.PERSIST})
	private List<AssignmentEntity> assignments;
	
	
	
	

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getAktsCredit() {
		return aktsCredit;
	}

	public void setAktsCredit(int aktsCredit) {
		this.aktsCredit = aktsCredit;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public Collection<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(Collection<UserEntity> users) {
		this.users = users;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<TimeSlotEntity> getTimeslots() {
		return timeslots;
	}

	public void setTimeslots(Collection<TimeSlotEntity> timeslots) {
		this.timeslots = timeslots;
	}

	public Collection<AnnouncementEntity> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(Collection<AnnouncementEntity> announcements) {
		this.announcements = announcements;
	}


	
	
	
	
}
