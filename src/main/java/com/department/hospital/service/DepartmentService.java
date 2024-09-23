package com.department.hospital.service;

import java.util.Optional;

import com.department.hospital.dto.DepartmentDto;
import com.department.hospital.dto.request.CreateUpdateDepartmentDto;


public interface DepartmentService {

	Optional<DepartmentDto> findDepartmentByName(String name);

	DepartmentDto createDepartment(CreateUpdateDepartmentDto departmentDto);

	Optional<DepartmentDto> updateDepartment(CreateUpdateDepartmentDto departmentDto);
}
