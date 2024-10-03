package com.department.hospital.component.patient;

import java.time.LocalDateTime;

import com.department.hospital.component.room.Room;
import com.department.hospital.component.doctor.Doctor;
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
