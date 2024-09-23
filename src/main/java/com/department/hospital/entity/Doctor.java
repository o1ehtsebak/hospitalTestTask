package com.department.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude = {"departments"})
@Entity
@Table(name = "doctors")
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String firstName;
	private String lastName;

	@ManyToMany(mappedBy = "doctors", cascade = {CascadeType.ALL})
	private List<Department> departments;

}
