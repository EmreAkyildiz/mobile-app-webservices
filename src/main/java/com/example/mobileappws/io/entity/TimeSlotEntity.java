package com.example.mobileappws.io.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "timeslots")
public class TimeSlotEntity implements Serializable {

	private static final long serialVersionUID = 3355473780911118572L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false)
	private String day;
	
	@Column(nullable=false)
	private int slot;
	
	@ManyToMany(mappedBy = "timeslots")
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
