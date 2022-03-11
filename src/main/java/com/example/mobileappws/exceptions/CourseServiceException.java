package com.example.mobileappws.exceptions;

public class CourseServiceException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public CourseServiceException(String message) {
		super(message);
	}
}
