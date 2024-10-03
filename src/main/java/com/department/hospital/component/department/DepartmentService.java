package com.department.hospital.component.department;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.department.hospital.component.room.RoomLoadDto;

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

	public CreateUpdateDepartmentResponseDto createDepartment(CreateDepartmentRequestDto departmentDto) {
		final Department newDepartment = new Department();
		updateDepartmentName(departmentDto.name(), newDepartment);
		return departmentMapper.departmentToDepartmentResponse(newDepartment);
	}

	private void updateDepartmentName(String name, Department newDepartment) {
		newDepartment.setName(name);
		departmentsRepository.save(newDepartment);
	}

	public Optional<CreateUpdateDepartmentResponseDto> updateDepartment(UpdateDepartmentRequestDto departmentDto) {
		return departmentsRepository.findById(departmentDto.getDepartmentId()).map(department -> {
			updateDepartmentName(departmentDto.getName(), department);

			return departmentMapper.departmentToDepartmentResponse(department);
		});
	}
}
