package com.department.hospital.component.doctor;

import com.department.hospital.component.patient.RegisterPatientResponseDto;

import java.util.List;


public record DoctorDto(long id, String firstName, String lastName, List<RegisterPatientResponseDto> patients) {
}
