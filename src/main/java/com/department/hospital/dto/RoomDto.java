package com.department.hospital.dto;

import java.util.List;

public record RoomDto(

		long id, int number, int numberOfPlaces, List<PatientDto> patients) {
}
