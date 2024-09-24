package com.department.hospital.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.department.hospital.dto.*;
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
	public DepartmentLoadDto getDepartmentLoadInfo(Long id) {
		final Department department = departmentsRepository.getReferenceById(id);

		final DepartmentLoadDto departmentLoadDto = new DepartmentLoadDto();
		departmentLoadDto.setId(department.getId());
		departmentLoadDto.setName(department.getName());

		final List<RoomLoadDto> collect = department.getRooms().stream().map(room -> {
			final int numberOfPlaces = room.getNumberOfPlaces();
			final int patientsInsideRoom = room.getPatients().size();

			final RoomLoadDto roomLoadDto = new RoomLoadDto();
			roomLoadDto.setId(room.getId());
			roomLoadDto.setNumber(room.getNumber());
			roomLoadDto.setNumberOfAvailablePlaces(numberOfPlaces - patientsInsideRoom);

			final BigDecimal loadPercentage = BigDecimal.valueOf(patientsInsideRoom)
					.divide(BigDecimal.valueOf(numberOfPlaces), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

			roomLoadDto.setLoadedPlacedPercent(String.format("Room is loaded by %s%%", loadPercentage));
			return roomLoadDto;
		}).collect(Collectors.toList());

		departmentLoadDto.setRooms(collect);

		return departmentLoadDto;
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

	@Override
	public List<Department> getAllDepartments() {
		return departmentsRepository.findAll();
	}
}
