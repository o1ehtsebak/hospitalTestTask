package com.department.hospital.dto;

import com.department.hospital.entity.Doctor;
import com.department.hospital.entity.Room;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class PatientDto {

	private long id;
	private String firstName;
	private String lastName;
	private LocalDate treatmentStartDate;
	private LocalDate treatmentEndDate;
	private long doctorId;
}
