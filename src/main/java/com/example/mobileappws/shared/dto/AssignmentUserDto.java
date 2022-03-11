package com.example.mobileappws.shared.dto;

import java.io.Serializable;


public class AssignmentUserDto implements Serializable{

	private static final long serialVersionUID = -1379113864432589559L;
	
	private long id;
	private int grade;
	private UserDto userDetails;
	private AssignmentDto assignmentDetails;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public UserDto getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDto userDetails) {
		this.userDetails = userDetails;
	}
	public AssignmentDto getAssignmentDetails() {
		return assignmentDetails;
	}
	public void setAssignmentDetails(AssignmentDto assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
	}
	
	
}
