package com.example.mobileappws.io.entity;

import java.io.Serializable;
import java.util.Collection;

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
@Table(name= "assignments")
public class AssignmentEntity implements Serializable{

	private static final long serialVersionUID = -5431933628746897085L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false)
	private String assignmentId;
	
	@Column(nullable=false)
	private String header;
	
	@Column()
	private String context;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="courses_id")
	private CourseEntity courseDetails;
	
	
	@OneToMany(mappedBy = "assignmentDetails",cascade = CascadeType.PERSIST)
	private Collection<AssignmentUserEntity> assignmentsUsers;


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


	public CourseEntity getCourseDetails() {
		return courseDetails;
	}


	public void setCourseDetails(CourseEntity courseDetails) {
		this.courseDetails = courseDetails;
	}


	public Collection<AssignmentUserEntity> getAssignmentsUsers() {
		return assignmentsUsers;
	}


	public void setAssignmentsUsers(Collection<AssignmentUserEntity> assignmentsUsers) {
		this.assignmentsUsers = assignmentsUsers;
	}
	
	
	
}
