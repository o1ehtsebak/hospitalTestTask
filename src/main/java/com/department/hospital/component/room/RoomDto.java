package com.department.hospital.component.room;

import java.util.List;

import com.department.hospital.component.patient.RegisterPatientResponseDto;


public record RoomDto(long id, int number, int numberOfPlaces, List<RegisterPatientResponseDto> patients) {
}
