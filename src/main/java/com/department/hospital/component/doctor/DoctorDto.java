package com.department.hospital.component.doctor;

import java.util.List;

import com.department.hospital.component.patient.PatientDto;


public record DoctorDto(long id, String firstName, String lastName, List<PatientDto> patients) {
}
