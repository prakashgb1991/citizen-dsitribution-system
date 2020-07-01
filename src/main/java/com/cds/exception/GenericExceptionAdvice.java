package com.cds.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cds.util.ResponseMessage;

@ControllerAdvice
public class GenericExceptionAdvice extends ResponseEntityExceptionHandler {

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException exc) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File too large!"));
	}
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(Exception.class)
	public ResponseEntity handleException(Exception exc) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Something Went wrong!"));
	}
}