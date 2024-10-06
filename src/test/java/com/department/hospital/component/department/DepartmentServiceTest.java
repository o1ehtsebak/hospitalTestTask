package com.department.hospital.component.department;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.department.hospital.component.patient.Patient;
import com.department.hospital.component.room.Room;


@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

	private static final long DEPARTMENT_ID = 1L;
	private static final String DEPARTMENT_NAME = "departmentName";
	private static final int INDEX_FIRST = 0;
	private static final int INDEX_SECOND = 1;
	private static final int ROOM_ONE_PATIENTS = 5;
	private static final int ROOM_TWO_PATIENTS = 10;
	private static final int ROOM_ONE_PLACES = 10;
	private static final int ROOM_TWO_PLACES = 100;

	@Mock
	DepartmentMapper departmentMapper;
	@Mock
	DepartmentRepository departmentRepository;

	@InjectMocks
	DepartmentService testedInstance;

	@Test
	void shouldFindDepartmentByName() {
		//given
		final Department department = new Department();
		when(departmentRepository.getDepartmentByName(DEPARTMENT_NAME)).thenReturn(Optional.of(department));
		final DepartmentDto expectedDepartment = mock(DepartmentDto.class);
		when(departmentMapper.departmentToDepartmentDto(department)).thenReturn(expectedDepartment);

		//when
		final Optional<DepartmentDto> actualDepartment = testedInstance.findDepartmentByName(DEPARTMENT_NAME);

		//then
		assertTrue(actualDepartment.isPresent());
		assertEquals(expectedDepartment, actualDepartment.get());
	}

	@Test
	void shouldNotFindDepartmentByName() {
		//given
		when(departmentRepository.getDepartmentByName(DEPARTMENT_NAME)).thenReturn(Optional.empty());

		//when
		final Optional<DepartmentDto> actualDepartment = testedInstance.findDepartmentByName(DEPARTMENT_NAME);

		//then
		assertTrue(actualDepartment.isEmpty());
	}

	@Test
	void shouldCreateNewDepartment() {
		//given
		final CreateUpdateDepartmentResponseDto expectedDepartment = mock(CreateUpdateDepartmentResponseDto.class);
		when(departmentMapper.departmentToDepartmentResponse(any(Department.class))).thenReturn(expectedDepartment);
		final CreateDepartmentRequestDto createDepartmentRequestDto = new CreateDepartmentRequestDto(DEPARTMENT_NAME);

		//when
		final CreateUpdateDepartmentResponseDto newDepartment = testedInstance.createDepartment(createDepartmentRequestDto);

		//then
		assertEquals(expectedDepartment, newDepartment);
	}

	@Test
	void shouldUpdateDepartment() {
		//given
		final CreateUpdateDepartmentResponseDto expectedDepartment = mock(CreateUpdateDepartmentResponseDto.class);
		when(departmentMapper.departmentToDepartmentResponse(any(Department.class))).thenReturn(expectedDepartment);
		final UpdateDepartmentRequestDto updateDepartmentRequestDto = new UpdateDepartmentRequestDto();
		updateDepartmentRequestDto.setDepartmentId(DEPARTMENT_ID);
		updateDepartmentRequestDto.setName(DEPARTMENT_NAME);
		final Department department = new Department();
		when(departmentRepository.findById(DEPARTMENT_ID)).thenReturn(Optional.of(department));

		//when
		final Optional<CreateUpdateDepartmentResponseDto> createUpdateDepartmentResponseDto = testedInstance
				.updateDepartment(updateDepartmentRequestDto);

		//then
		assertTrue(createUpdateDepartmentResponseDto.isPresent());
		assertEquals(DEPARTMENT_NAME, department.getName());
	}

	@Test
	void shouldGetDepartmentLoadInfo() {
		//given
		final Room roomOne = createRoom(ROOM_ONE_PLACES, ROOM_ONE_PATIENTS);
		int roomOneAvailablePlaces = ROOM_ONE_PLACES - ROOM_ONE_PATIENTS;
		final Room roomTwo = createRoom(ROOM_TWO_PLACES, ROOM_TWO_PATIENTS);
		int roomTwoAvailablePlaces = ROOM_TWO_PLACES - ROOM_TWO_PATIENTS;
		final Department department = new Department();
		department.setRooms(List.of(roomOne, roomTwo));
		department.setId(DEPARTMENT_ID);
		department.setName(DEPARTMENT_NAME);
		when(departmentRepository.getReferenceById(DEPARTMENT_ID)).thenReturn(department);

		//when
		final DepartmentLoadDto departmentLoadInfo = testedInstance.getDepartmentLoadInfo(DEPARTMENT_ID);

		//then
		assertEquals(DEPARTMENT_ID, departmentLoadInfo.id());
		assertEquals(DEPARTMENT_NAME, departmentLoadInfo.name());
		assertNotNull(departmentLoadInfo.rooms());
		assertFalse(departmentLoadInfo.rooms().isEmpty());
		assertEquals(roomOneAvailablePlaces, departmentLoadInfo.rooms().get(INDEX_FIRST).numberOfAvailablePlaces());
		assertEquals(roomTwoAvailablePlaces, departmentLoadInfo.rooms().get(INDEX_SECOND).numberOfAvailablePlaces());
	}

	private Room createRoom(int numberOfPlaces, int numberOfPatients) {
		final Room room = new Room();
		room.setNumberOfPlaces(numberOfPlaces);
		room.setPatients(createPatients(numberOfPatients));
		return room;
	}

	private List<Patient> createPatients(int numberOfPatients) {
		return Stream.iterate(INTEGER_ZERO, integer -> integer + 1).limit(numberOfPatients).map(ignored -> new Patient())
				.collect(Collectors.toList());

	}
}