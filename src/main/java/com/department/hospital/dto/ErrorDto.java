package com.department.hospital.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDto {

	private String errorCode;
	private String errorMsg;

}
