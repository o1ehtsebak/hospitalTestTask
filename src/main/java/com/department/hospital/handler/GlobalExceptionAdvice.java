package com.department.hospital.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorDto> handleEntityNotFoundException(final EntityNotFoundException entityNotFoundException) {
		final ErrorDto entityNotFound = new ErrorDto("entityNotFound",
				String.format("There is no such entity - [%s]", entityNotFoundException.getMessage()));
		return ResponseEntity.badRequest().body(entityNotFound);
	}

}
