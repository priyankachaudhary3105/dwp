package org.dwp.dwpcityusers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * ServiceException is custom service exception wrapper
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	private String description;

	private int httpStatus;

	public ServiceException(String errorMessage, String description, int httpStatus) {
		super(errorMessage);
		this.description = description;
		this.httpStatus = httpStatus;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getDescription() {
		return description;
	}

}
