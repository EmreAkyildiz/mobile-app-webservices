package com.example.mobileappws.shared.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class UserDto implements Serializable{

	private static final long serialVersionUID=4865903039190150223l;
	private long id;
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String encryptedPassword;
	private String emailVerificationToken;
	private Boolean emailVerificationStatus;
	private List<AddressDTO> addresses;
	private Collection<String> roles;
	private Collection<CourseDto> courses;
	private Collection<AnnouncementDto> announcements;
	private Collection<AssignmentUserDto> assignmentsUsers;
	
	
	
	public UserDto() {
		// TODO Auto-generated constructor stub
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}
	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}
	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}
	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}

	public List<AddressDTO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressDTO> addresses) {
		this.addresses = addresses;
	}

	public Collection<String> getRoles() {
		return roles;
	}

	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}
	public Collection<CourseDto> getCourses() {
		return courses;
	}
	public void setCourses(Collection<CourseDto> courses) {
		this.courses = courses;
	}

	public Collection<AnnouncementDto> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(Collection<AnnouncementDto> announcements) {
		this.announcements = announcements;
	}

	public Collection<AssignmentUserDto> getAssignmentsUsers() {
		return assignmentsUsers;
	}

	public void setAssignmentsUsers(Collection<AssignmentUserDto> assignmentsUsers) {
		this.assignmentsUsers = assignmentsUsers;
	}
	
	
}
