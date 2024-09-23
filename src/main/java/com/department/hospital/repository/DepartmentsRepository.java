package com.department.hospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.department.hospital.entity.Department;


@Repository
public interface DepartmentsRepository extends JpaRepository<Department, Long> {

	Optional<Department> getDepartmentByName(final String name);
}
