package com.example.mobileappws.ui.model.response;

public enum ErrorMessages {

	MISSING_REQUIRED_FIELD("Missing required field. check document"),
	RECORD_ALREADY_EXISTS("Record already exists"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	NO_RECORD_FOUND("Record with provided id is not found"),
	AUTHENTICATION_FAILED("Authentication failed"),
	COULD_NOT_UPDATE_RECORD("Couldn't update record"),
	COULD_NOT_DELETE_RECORD("Couldn't delete record"),
	EMAIL_ADDRESS_NOT_VERIFIED("Email address couldn't be verified");
	
	private String errorMessage;
	
	ErrorMessages(String errorMessage) {
		this.errorMessage=errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
	
}
