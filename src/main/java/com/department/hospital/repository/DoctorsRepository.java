package com.department.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.department.hospital.entity.Doctor;


@Repository
public interface DoctorsRepository extends JpaRepository<Doctor, Long> {
}
