package com.test.notes.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value= {NoteNotFoundException.class})
	public ResponseEntity<Object> handleNotesException(NoteNotFoundException ex, WebRequest request){
		return new ResponseEntity<Object>(ex,new HttpHeaders(),HttpStatus.NOT_FOUND);
	}
}
