package com.department.hospital.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.*;

import com.department.hospital.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.department.hospital.dto.DepartmentLoadDto;
import com.department.hospital.dto.RoomLoadDto;
import com.department.hospital.entity.*;
import com.department.hospital.mapper.DepartmentMapper;
import com.department.hospital.repository.DepartmentsRepository;


@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

	private static final long DEPARTMENT_ID = 1L;
	private static final int ROOM_NUMBER_OF_PLACES = 100;
	@Mock
	private DepartmentsRepository departmentsRepository;
	@Mock
	private DepartmentMapper departmentMapper;

	@InjectMocks
	private DepartmentServiceImpl testedInstance;

	@Test
	void shouldCalculateDepartmentLoad() {
		//given
		final Department department = mock(Department.class);
		final Room room = mock(Room.class);
		when(room.getNumberOfPlaces()).thenReturn(ROOM_NUMBER_OF_PLACES);
		final List<Patient> patients = Arrays.asList(mock(Patient.class), mock(Patient.class), mock(Patient.class),
				mock(Patient.class));
		when(room.getPatients()).thenReturn(patients);
		when(department.getRooms()).thenReturn(Collections.singletonList(room));
		when(departmentsRepository.getReferenceById(DEPARTMENT_ID)).thenReturn(department);

		final int expectedAvailablePlaced = ROOM_NUMBER_OF_PLACES - patients.size();
		final String expectedLoadPercent = String.format("%s", Double.valueOf(patients.size()));

		//when
		final DepartmentLoadDto departmentLoadInfo = testedInstance.getDepartmentLoadInfo(DEPARTMENT_ID);

		//then
		assertNotNull(departmentLoadInfo.rooms());

		final RoomLoadDto roomLoadDto = departmentLoadInfo.rooms().get(0);
		assertEquals(expectedAvailablePlaced, roomLoadDto.numberOfAvailablePlaces());
		assertTrue(roomLoadDto.loadedPlacesPercentage().contains(expectedLoadPercent));
	}
}