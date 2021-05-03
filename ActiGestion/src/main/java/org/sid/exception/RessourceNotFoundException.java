package org.sid.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RessourceNotFoundException extends Exception {
	private static final Long serialVersionUID = 1L;

	public RessourceNotFoundException(String message) {
		super ( message );
	}
}
