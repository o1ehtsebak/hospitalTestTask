package com.department.hospital.service;

import java.util.List;
import java.util.Optional;

import com.department.hospital.dto.DepartmentDto;
import com.department.hospital.dto.DepartmentLoadDto;
import com.department.hospital.dto.request.CreateUpdateDepartmentDto;
import com.department.hospital.entity.Department;


public interface DepartmentService {

	Optional<DepartmentDto> findDepartmentByName(String name);

	DepartmentLoadDto getDepartmentLoadInfo(Long id);

	DepartmentDto createDepartment(CreateUpdateDepartmentDto departmentDto);

	Optional<DepartmentDto> updateDepartment(CreateUpdateDepartmentDto departmentDto);

	List<Department> getAllDepartments();
}
