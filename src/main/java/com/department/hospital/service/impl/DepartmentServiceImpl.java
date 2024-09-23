package com.department.hospital.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.department.hospital.dto.DepartmentDto;
import com.department.hospital.dto.request.CreateUpdateDepartmentDto;
import com.department.hospital.entity.Department;
import com.department.hospital.mapper.DepartmentMapper;
import com.department.hospital.repository.DepartmentsRepository;
import com.department.hospital.service.DepartmentService;


@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentsRepository departmentsRepository;
	@Autowired
	private DepartmentMapper departmentMapper;

	@Override
	public Optional<DepartmentDto> findDepartmentByName(String name) {
		return departmentsRepository.getDepartmentByName(name).map(departmentMapper::departmentToDepartmentDto);
	}

	@Override
	public DepartmentDto createDepartment(CreateUpdateDepartmentDto departmentDto) {
		final Department newDepartment = new Department();
		updateDepartmentName(departmentDto.getName(), newDepartment);
		return departmentMapper.departmentToDepartmentDto(newDepartment);
	}

	private void updateDepartmentName(String name, Department newDepartment) {
		newDepartment.setName(name);
		departmentsRepository.save(newDepartment);
	}

	@Override
	public Optional<DepartmentDto> updateDepartment(CreateUpdateDepartmentDto departmentDto) {
		return departmentsRepository.findById(departmentDto.getDepartmentId()).map(department -> {
			updateDepartmentName(departmentDto.getName(), department);

			return departmentMapper.departmentToDepartmentDto(department);
		});
	}
}
