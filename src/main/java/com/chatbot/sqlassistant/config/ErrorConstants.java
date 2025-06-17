package com.chatbot.sqlassistant.config;

public final class ErrorConstants {

	public static final Integer NO_CONTENT_CODE = 204;
	public static final String NO_CONTENT_MESSAGE = "No content to display!";

	public static final Integer BAD_REQUEST_CODE = 400;
	public static final String BAD_REQUEST_MESSAGE = "Bad request. Required fields are not present in request!";

	public static final Integer NOT_FOUND_CODE = 404;

	public static final Integer INVALID_INPUT_ERROR_CODE = 406;
	public static final String INVALID_INPUT_ERROR = "Invalid Input!";

	public static final Integer DUPLICATE_DATA = 409;

	public static final Integer OBJECT_ALREADY_EXSISTS = 409;
	public static final String OBJECT_ALREADY_EXSISTS_ERROR = "Object already exists!";

	public static final Integer INTERNAL_SERVER_ERROR_CODE = 500;
	public static final String INTERNAL_SERVER_ERROR = "Internal server error!";
	
	public static final Integer PROCESSING_ERROR_CODE = 501;
	
	public static final String RESOURCE_AUTH_MESSAGE = "You are not authorized to access this page";

	public static final Integer UNAUTHORIZED = 401;
	public static final Integer FORBIDDEN = 403;
	public static final String SESSION_EXPIRED = "Your session has expired. Please login again!";
	public static final String MISSING_AUTH = "Missing authorization header!";
	public static final String MALFORMED_AUTH = "Malformed authentication token!";
	

}
