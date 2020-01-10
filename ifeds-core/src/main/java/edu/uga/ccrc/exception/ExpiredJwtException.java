package edu.uga.ccrc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExpiredJwtException extends Exception{

	private static final long serialVersionUID = -3675490681998679760L;
	
	public ExpiredJwtException(String exception) {
		super(exception);
	}

}
