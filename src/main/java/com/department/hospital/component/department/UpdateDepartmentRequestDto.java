package com.department.hospital.component.department;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UpdateDepartmentRequestDto {

	private Long departmentId;
	@Valid
	@NotEmpty
	private String name;

}
