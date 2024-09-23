package com.department.hospital.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CreateUpdateDepartmentDto {

	private Long departmentId;
	@Valid
	@NotEmpty
	private String name;

}
