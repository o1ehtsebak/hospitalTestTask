package com.department.hospital.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class DepartmentLoadDto {

	private long id;
	private String name;
	private List<RoomLoadDto> rooms;

}
