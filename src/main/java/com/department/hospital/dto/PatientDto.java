package com.department.hospital.dto;

import java.time.LocalDate;


public record PatientDto(long id, String firstName, String lastName, LocalDate treatmentStartDate, LocalDate treatmentEndDate,
		long doctorId) {
}
