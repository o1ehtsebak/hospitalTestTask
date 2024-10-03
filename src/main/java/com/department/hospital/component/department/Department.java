package com.department.hospital.component.department;

import java.util.List;

import com.department.hospital.component.room.Room;
import com.department.hospital.component.doctor.Doctor;
import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "departments")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String name;
	@OneToMany(mappedBy = "department")
	private List<Room> rooms;
	@ManyToMany
	@JoinTable(name = "department2doctor", joinColumns = @JoinColumn(name = "department_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"), uniqueConstraints = { @UniqueConstraint(columnNames = { "department_id", "doctor_id" }) })
	private List<Doctor> doctors;

}
