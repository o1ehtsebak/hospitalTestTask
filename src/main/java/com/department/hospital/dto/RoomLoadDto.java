package com.department.hospital.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RoomLoadDto {

	private long id;
	private int number;
	private int numberOfAvailablePlaces;
	private String loadedPlacesPercent;
}
