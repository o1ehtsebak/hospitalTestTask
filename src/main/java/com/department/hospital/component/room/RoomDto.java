package com.department.hospital.component.room;

import com.department.hospital.component.patient.PatientDto;

import java.util.List;

public record RoomDto(

		long id, int number, int numberOfPlaces, List<PatientDto> patients) {
}
