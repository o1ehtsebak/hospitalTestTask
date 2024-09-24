package com.department.hospital.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class RoomDto {

	private long id;
	private int number;
	private int numberOfPlaces;
	private List<PatientDto> patients;
}
