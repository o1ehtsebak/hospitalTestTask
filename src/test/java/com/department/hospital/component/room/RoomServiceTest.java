package com.department.hospital.component.room;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.department.hospital.component.department.Department;
import com.department.hospital.component.department.DepartmentRepository;

import jakarta.persistence.EntityNotFoundException;


@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

	private static final long DEPARTMENT_ID = 1L;
	private static final int ROOM_NUMBER = 10;
	private static final int NUMBER_OF_PLACES = 50;
	private static final int NEW_ROOM_NUMBER = 17;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	@Mock
	RoomRepository roomRepository;
	@Mock
	DepartmentRepository departmentRepository;
	@Mock
	RoomMapper roomMapper;

	@InjectMocks
	RoomService testedInstance;

	@Test
	void shouldFindRoom() {
		//given
		final Room room = new Room();
		when(roomRepository.getRoomByDepartmentIdAndNumber(DEPARTMENT_ID, ROOM_NUMBER)).thenReturn(Optional.of(room));
		final RoomDto expectedRoomDto = mock(RoomDto.class);
		when(roomMapper.roomToRoomDto(room)).thenReturn(expectedRoomDto);

		//when
		final Optional<RoomDto> roomDto = testedInstance.findRoom(DEPARTMENT_ID, ROOM_NUMBER);

		//then
		assertTrue(roomDto.isPresent());
		assertEquals(expectedRoomDto, roomDto.get());
	}

	@Test
	void shouldCreateRoom() {
		//given
		final CreateUpdateRoomRequestDto createUpdateRoomRequestDto = new CreateUpdateRoomRequestDto();
		createUpdateRoomRequestDto.setDepartmentId(DEPARTMENT_ID);
		final Department department = new Department();
		department.setId(DEPARTMENT_ID);
		when(departmentRepository.findById(DEPARTMENT_ID)).thenReturn(Optional.of(department));
		final Room createdRoom = new Room();
		when(roomMapper.createUpdateRoomDtoToRoom(createUpdateRoomRequestDto)).thenReturn(createdRoom);
		final RoomDto expectedRoomDto = mock(RoomDto.class);
		when(roomMapper.roomToRoomDto(createdRoom)).thenReturn(expectedRoomDto);

		//when
		final CreateUpdateRoomResponseDto createRoomResponse = testedInstance.createRoom(createUpdateRoomRequestDto);

		//then
		assertEquals(department, createdRoom.getDepartment());
		assertEquals(expectedRoomDto, createRoomResponse.room());
	}

	@Test
	void shouldNotCreateRoomWhenDepartmentDoesNotExists() {
		//given
		final CreateUpdateRoomRequestDto createUpdateRoomRequestDto = new CreateUpdateRoomRequestDto();
		createUpdateRoomRequestDto.setDepartmentId(DEPARTMENT_ID);
		when(departmentRepository.findById(DEPARTMENT_ID)).thenReturn(Optional.empty());

		//when
		assertThrows(EntityNotFoundException.class, () -> {
			testedInstance.createRoom(createUpdateRoomRequestDto);
		});

		//then expect exception
	}

	@Test
	void shouldUpdateRoom() {
		//given
		final CreateUpdateRoomRequestDto createUpdateRoomRequestDto = new CreateUpdateRoomRequestDto();
		createUpdateRoomRequestDto.setDepartmentId(DEPARTMENT_ID);
		createUpdateRoomRequestDto.setNumberOfPlaces(NUMBER_OF_PLACES);
		createUpdateRoomRequestDto.setNumber(NEW_ROOM_NUMBER);
		createUpdateRoomRequestDto.setOriginalNumber(ROOM_NUMBER);
		final Room existingRoom = new Room();
		when(roomRepository.getRoomByDepartmentIdAndNumber(DEPARTMENT_ID, ROOM_NUMBER)).thenReturn(Optional.of(existingRoom));
		final RoomDto expectedRoomDto = mock(RoomDto.class);
		when(roomMapper.roomToRoomDto(existingRoom)).thenReturn(expectedRoomDto);

		//when
		final Optional<CreateUpdateRoomResponseDto> updateRoomResponse = testedInstance.updateRoom(createUpdateRoomRequestDto);

		//then
		assertEquals(NUMBER_OF_PLACES, existingRoom.getNumberOfPlaces());
		assertEquals(NEW_ROOM_NUMBER, existingRoom.getNumber());

		assertTrue(updateRoomResponse.isPresent());
		assertEquals(expectedRoomDto, updateRoomResponse.get().room());
	}
}