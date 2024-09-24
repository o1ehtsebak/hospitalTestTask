package com.department.hospital.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.department.hospital.entity.Patient;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	//	TODO: Add support for retrieving patients by pages
	List<Patient> findAllByTreatmentEndDateLessThanAndReleasedIsFalse(LocalDateTime treatmentEndDate);

}
