package com.department.hospital.component.doctor;

import com.department.hospital.component.patient.PatientDto;

import java.util.List;


public record DoctorDto(long id, String firstName, String lastName, List<PatientDto> patients) {
}
