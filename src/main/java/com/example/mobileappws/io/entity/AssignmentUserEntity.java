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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "assignments_users")
public class AssignmentUserEntity implements Serializable{

	private static final long serialVersionUID = 593028241472635564L;

	@Id
	@GeneratedValue
	private long id;
		
	@Column(nullable=true)
	private int grade;
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="users_id")
	private UserEntity userDetails;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="assignment_id")
	private AssignmentEntity assignmentDetails;


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


	public UserEntity getUserDetails() {
		return userDetails;
	}


	public void setUserDetails(UserEntity userDetails) {
		this.userDetails = userDetails;
	}


	public AssignmentEntity getAssignmentDetails() {
		return assignmentDetails;
	}


	public void setAssignmentDetails(AssignmentEntity assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
	}
	
	
}
