package com.department.hospital.dto;

import java.util.List;


public record DoctorDto(long id, String firstName, String lastName, List<PatientDto> patients) {
}
