package edu.uga.ccrc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SQLException extends Exception{
	
	private static final long serialVersionUID = -443582129255538535L;

	public SQLException(String exception) {
		super(exception);
	}

}
