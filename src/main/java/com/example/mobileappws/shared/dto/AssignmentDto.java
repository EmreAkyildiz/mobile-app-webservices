package com.example.mobileappws.shared.dto;

import java.io.Serializable;
import java.util.Collection;




public class AssignmentDto implements Serializable {


	private static final long serialVersionUID = -5923449133519732122L;
	private long id;
	private String assignmentId;
	private String header;
	private String context;
	private CourseDto courseDto;
	private Collection<AssignmentUserDto> assignmentsUsers;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public CourseDto getCourseDto() {
		return courseDto;
	}
	public void setCourseDto(CourseDto courseDto) {
		this.courseDto = courseDto;
	}
	public Collection<AssignmentUserDto> getAssignmentsUsers() {
		return assignmentsUsers;
	}
	public void setAssignmentsUsers(Collection<AssignmentUserDto> assignmentsUsers) {
		this.assignmentsUsers = assignmentsUsers;
	}
	
	
	
}
