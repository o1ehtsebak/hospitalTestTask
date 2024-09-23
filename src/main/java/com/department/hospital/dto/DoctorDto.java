package com.department.hospital.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class DoctorDto {

	private long id;
	private String firstName;
	private String lastName;
	private List<DepartmentDto> departments;
}
