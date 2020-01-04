package edu.uga.ccrc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 9072696029611064481L;

	public EntityNotFoundException(String exception) {
		super(exception);
	}
}
