package com.example.mobileappws.shared.dto;

import java.io.Serializable;
import java.util.Collection;


import com.example.mobileappws.io.entity.CourseEntity;

public class TimeSlotDto implements Serializable{

	private static final long serialVersionUID = 3190732611627804286L;
	private long id;
	private String day;
	private int slot;
	private Collection<CourseEntity> courses;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getSlot() {
		return slot;
	}
	public void setSlot(int slot) {
		this.slot = slot;
	}
	public Collection<CourseEntity> getCourses() {
		return courses;
	}
	public void setCourses(Collection<CourseEntity> courses) {
		this.courses = courses;
	}
	
	
	
	
}
