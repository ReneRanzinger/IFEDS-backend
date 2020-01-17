package edu.uga.ccrc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoResponeException extends Exception{
	
	private static final long serialVersionUID = 5041365856020288311L;

	public NoResponeException(String exception) {
		super(exception);
	}

}
