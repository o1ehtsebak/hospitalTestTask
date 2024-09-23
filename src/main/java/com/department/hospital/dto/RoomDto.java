package com.department.hospital.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RoomDto {

	private long id;
	private int number;
	private int numberOfPlaces;
	private DepartmentDto department;
}
