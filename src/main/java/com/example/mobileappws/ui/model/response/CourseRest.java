package com.example.mobileappws.ui.model.response;

import java.util.Collection;


public class CourseRest {

	private String courseId;
	private String courseName;
	private int aktsCredit;
	private int section;
	private Collection<UserRest> users;
	private Collection<TimeSlotRest> timeslots;
	
	public CourseRest() {
		// TODO Auto-generated constructor stub
	}
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

	public Collection<UserRest> getUsers() {
		return users;
	}
	public void setUsers(Collection<UserRest> users) {
		this.users = users;
	}
	public Collection<TimeSlotRest> getTimeslots() {
		return timeslots;
	}
	public void setTimeslots(Collection<TimeSlotRest> timeslots) {
		this.timeslots = timeslots;
	}
	
	
	
}
