package com.projectreveal.reveal.backend.exceptionhandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.UnprocessableEntity;

import com.projectreveal.reveal.backend.logging.LoggingConstants;

@ControllerAdvice
public class ExceptionHandlerGeneric {
	Logger logger = LogManager.getLogger(ExceptionHandlerGeneric.class);
	
	@ExceptionHandler(value = {NumberFormatException.class,MissingServletRequestParameterException.class})
	public ResponseEntity<GenericException> handleBadRequests(Exception parentException){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.BAD_REQUEST,parentException.getStackTrace());
		GenericException genericException = new GenericException("Malformed or Missing Parameters");
		return new ResponseEntity<GenericException>(genericException, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(value = ResourceNotFound.class)
	public ResponseEntity<GenericException> handleUnknownResources(ResourceNotFound exception){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.NOT_FOUND,exception.getStackTrace());
		GenericException genericException = new GenericException(exception.getMessage());
		return new ResponseEntity<GenericException>(genericException, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = RegistrationFailed.class)
	public ResponseEntity<GenericException> handleFailureRegistrations(RuntimeException parentException){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.CONFLICT,parentException.getStackTrace());
		GenericException genericException = new GenericException(parentException.getMessage());
		return new ResponseEntity<GenericException>(genericException, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<GenericException> handleIncorrectMethods(HttpRequestMethodNotSupportedException exception){
		 
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.METHOD_NOT_ALLOWED,exception.getStackTrace());
		GenericException genericException = new GenericException(exception.getMessage());
		return new ResponseEntity<GenericException>(genericException, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(value = UnprocessableRequest.class)
	public ResponseEntity<GenericException> handleUnprocessableRequests(UnprocessableRequest exception){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.UNPROCESSABLE_ENTITY,exception.getStackTrace());
		GenericException genericException = new GenericException(exception.getMessage());
		return new ResponseEntity<GenericException>(genericException, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<GenericException> Megahandler(Exception exception){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.INTERNAL_SERVER_ERROR,exception.getStackTrace());
		GenericException genericException = new GenericException("Unexpected Error Occured");
		return new ResponseEntity<GenericException>(genericException, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
