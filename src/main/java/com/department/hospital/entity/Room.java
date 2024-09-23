package com.department.hospital.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@ToString(exclude = { "department" })
@Entity
@Table(name = "rooms")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private int number;
	private int numberOfPlaces;

	@ManyToOne(optional = false)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;
}
