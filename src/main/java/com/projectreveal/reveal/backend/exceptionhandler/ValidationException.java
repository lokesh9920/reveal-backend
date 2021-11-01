package com.projectreveal.reveal.backend.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ValidationException extends RuntimeException {

	private String message;

}
