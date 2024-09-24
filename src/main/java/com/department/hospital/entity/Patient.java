package com.department.hospital.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;

	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	private LocalDateTime treatmentStartDate;
	private LocalDateTime treatmentEndDate;
	private boolean released;

}
