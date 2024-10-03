package com.department.hospital.component.doctor;


import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UpdateDoctorRequestDto {

	private Long id;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotNull
	private List<Long> departments;

}
