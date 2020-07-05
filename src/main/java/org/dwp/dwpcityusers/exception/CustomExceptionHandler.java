package org.dwp.dwpcityusers.exception;

import org.dwp.dwpcityusers.domain.ErrorResponse;
import org.dwp.dwpcityusers.service.BPDTSUserProxyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({ "unchecked", "rawtypes" })
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	Logger logger = LoggerFactory.getLogger(BPDTSUserProxyServiceImpl.class);

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		logger.error("Unhadled Exception in service :" + ex.getMessage());
		ErrorResponse error = new ErrorResponse("Internal Server Error");
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ServiceException.class)
	public final ResponseEntity<Object> handleServiceException(ServiceException ex, WebRequest request) {
		logger.error("ServiceException in service :" + ex.getMessage());
		ErrorResponse error = new ErrorResponse(ex.getMessage());
		error.setDetails(ex.getDescription());
		return new ResponseEntity(error, HttpStatus.valueOf(ex.getHttpStatus()));
	}

}