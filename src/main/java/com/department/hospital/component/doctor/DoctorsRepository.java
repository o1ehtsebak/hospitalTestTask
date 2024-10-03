package com.department.hospital.component.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.department.hospital.component.doctor.Doctor;


@Repository
public interface DoctorsRepository extends JpaRepository<Doctor, Long> {
}
