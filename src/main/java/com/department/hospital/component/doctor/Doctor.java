package com.department.hospital.component.doctor;

import com.department.hospital.component.department.Department;
import com.department.hospital.component.patient.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude = {"departments", "patients"})
@Entity
@Table(name = "doctors")
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	@ManyToMany(mappedBy = "doctors", cascade = {CascadeType.ALL})
	private List<Department> departments;
	@OneToMany(mappedBy = "doctor")
	private List<Patient> patients;

}
