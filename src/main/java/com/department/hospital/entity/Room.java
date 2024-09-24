package com.department.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@ToString(exclude = { "department", "patients"})
@Entity
@Table(name = "rooms", uniqueConstraints = { @UniqueConstraint(columnNames = { "number", "department_id" }) })
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	private int number;
	private int numberOfPlaces;

	@ManyToOne(optional = false)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;

	@OneToMany(mappedBy = "room")
	private List<Patient> patients;
}
