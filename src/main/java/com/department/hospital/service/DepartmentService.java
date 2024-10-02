package com.department.hospital.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.department.hospital.dto.*;
import com.department.hospital.dto.request.CreateUpdateDepartmentDto;
import com.department.hospital.entity.Department;
import com.department.hospital.mapper.DepartmentMapper;
import com.department.hospital.repository.DepartmentsRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class DepartmentService {

	private final DepartmentMapper departmentMapper;
	private final DepartmentsRepository departmentsRepository;

	public Optional<DepartmentDto> findDepartmentByName(String name) {
		return departmentsRepository.getDepartmentByName(name).map(departmentMapper::departmentToDepartmentDto);
	}

	public DepartmentLoadDto getDepartmentLoadInfo(Long id) {
		final Department department = departmentsRepository.getReferenceById(id);

		final List<RoomLoadDto> roomsLoadDto = department.getRooms().stream().map(room -> {
			final int numberOfPlaces = room.getNumberOfPlaces();
			final int patientsInsideRoom = room.getPatients().size();
			final int numberOfAvailablePlaces = numberOfPlaces - patientsInsideRoom;
			final BigDecimal loadPercentage = BigDecimal.valueOf(patientsInsideRoom)
					.divide(BigDecimal.valueOf(numberOfPlaces), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
			final String loadedPlacesPercentage = String.format("Room is loaded by %s %%", loadPercentage);
			return new RoomLoadDto(room.getId(), room.getNumber(), numberOfAvailablePlaces, loadedPlacesPercentage);
		}).collect(Collectors.toList());

		return new DepartmentLoadDto(department.getId(), department.getName(), roomsLoadDto);
	}

	public DepartmentDto createDepartment(CreateUpdateDepartmentDto departmentDto) {
		final Department newDepartment = new Department();
		updateDepartmentName(departmentDto.getName(), newDepartment);
		return departmentMapper.departmentToDepartmentDto(newDepartment);
	}

	private void updateDepartmentName(String name, Department newDepartment) {
		newDepartment.setName(name);
		departmentsRepository.save(newDepartment);
	}

	public Optional<DepartmentDto> updateDepartment(CreateUpdateDepartmentDto departmentDto) {
		return departmentsRepository.findById(departmentDto.getDepartmentId()).map(department -> {
			updateDepartmentName(departmentDto.getName(), department);

			return departmentMapper.departmentToDepartmentDto(department);
		});
	}

	public List<Department> getAllDepartments() {
		return departmentsRepository.findAll();
	}
}
