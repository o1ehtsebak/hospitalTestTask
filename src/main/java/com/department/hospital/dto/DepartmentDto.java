package com.department.hospital.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class DepartmentDto {

	private long id;
	private String name;
	private List<RoomDto> rooms;
	private List<DoctorDto> doctors;

}
