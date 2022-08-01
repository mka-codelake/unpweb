package de.codelake.unpweb.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { EntityNotFoundException.class })
	protected ResponseEntity<Object> handleException(final RuntimeException exception, final WebRequest request) {
		return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

}
