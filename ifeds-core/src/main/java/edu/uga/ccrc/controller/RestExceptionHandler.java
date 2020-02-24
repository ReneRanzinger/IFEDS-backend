package edu.uga.ccrc.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import edu.uga.ccrc.exception.EntityNotFoundException;
import edu.uga.ccrc.exception.ErrorDetails;
import edu.uga.ccrc.exception.ExpiredJwtException;
import edu.uga.ccrc.exception.ForbiddenException;
import edu.uga.ccrc.exception.NoResponeException;
import edu.uga.ccrc.exception.SQLException;

@CrossOrigin
@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex,WebRequest request) {
		ErrorDetails error = new ErrorDetails();
		error.setTimestamp(new Date());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());	
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	protected ResponseEntity<Object> handleForbiddenException(ForbiddenException ex,WebRequest request) {
		ErrorDetails error = new ErrorDetails();
		error.setTimestamp(new Date());
		error.setStatus(HttpStatus.FORBIDDEN.value());
		error.setMessage(ex.getMessage());	
		return new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	protected ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex,WebRequest request) {
		ErrorDetails error = new ErrorDetails();
		error.setTimestamp(new Date());
		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		error.setMessage(ex.getMessage());	
		return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(SQLException.class)
	protected ResponseEntity<Object> handleSQLException(SQLException ex,WebRequest request) {
		ErrorDetails error = new ErrorDetails();
		error.setTimestamp(new Date());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());	
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoResponeException.class)
	protected ResponseEntity<Object> handleNoResponseException(WebRequest request) {
		ErrorDetails error = new ErrorDetails();
		error.setTimestamp(new Date());
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage("Something went wrong. Please try after sometime");	
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
}
