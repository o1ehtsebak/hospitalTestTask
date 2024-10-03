package com.department.hospital.component.department;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	Optional<Department> getDepartmentByName(final String name);
}
