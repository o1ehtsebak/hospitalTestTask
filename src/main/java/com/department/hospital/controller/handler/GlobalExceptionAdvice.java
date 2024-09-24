package com.department.hospital.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.department.hospital.dto.ErrorDto;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorDto> handleEntityNotFoundException(final EntityNotFoundException entityNotFoundException) {
		final ErrorDto errorDto = new ErrorDto();
		errorDto.setErrorCode("entityNotFound");
		errorDto.setErrorMsg(String.format("There is no such entity - [%s]", entityNotFoundException.getMessage()));

		return ResponseEntity.badRequest().body(errorDto);
	}

}
