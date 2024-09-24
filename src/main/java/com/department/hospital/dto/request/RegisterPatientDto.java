package com.department.hospital.dto.request;


import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RegisterPatientDto {

	private Long id;
	@Valid
	@NotEmpty
	private String firstName;
	@Valid
	@NotEmpty
	private String lastName;
	//	TODO: validation for dates should be added - start date can't be after end date
	@NotNull
	private LocalDateTime treatmentStartDate;
	@NotNull
	private LocalDateTime treatmentEndDate;
	@NotNull
	private Long doctorId;
	@NotNull
	private Long roomId;

}
