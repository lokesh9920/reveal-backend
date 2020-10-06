package com.projectreveal.reveal.backend.logging;

public class LoggingConstants {
	
	public static final String REQUEST_LOGGER = "[Request Recieved]: {}: {}";
	public static final String FAILED_VALIDATION = "[Validation Failed]: Recieved {} = {}";
	public static final String RESPONSE_BODY = "[Response] to {}:{} is {} and content is {}";
	
	public static final String REST_REQUEST_ATTEMPT = "[Rest Request] Attempting to reach {} to {}";
	public static final String REST_REQUEST_STATUS_REPORT= "[Rest Request Report] {} responded with {}";
	

	public static final String HANDLED_EXCEPTION = "[EXCEPTION HANDLED] Responded with: {}, The stack trace is: {}";
}
