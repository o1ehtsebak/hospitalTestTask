package com.department.hospital.component.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;


@ExtendWith(MockitoExtension.class)
class RoomControllerTest {

	private static final long DEPARTMENT_ID = 1L;
	private static final int ROOM_NUMBER = 10;

	@Mock
	RoomService roomService;

	@InjectMocks
	RoomController testedInstance;

	@Test
	void shouldGetRoom() {
		//given
		final RoomDto expectedRoom = mock(RoomDto.class);
		when(roomService.findRoom(DEPARTMENT_ID, ROOM_NUMBER)).thenReturn(Optional.of(expectedRoom));

		//when
		final ResponseEntity<RoomDto> roomResponse = testedInstance.getRoom(DEPARTMENT_ID, ROOM_NUMBER);

		//then
		assertEquals(expectedRoom, roomResponse.getBody());
	}

	@Test
	void shouldCreateRoom() {
		//given
		final RoomDto room = mock(RoomDto.class);
		final CreateUpdateRoomRequestDto createRoomRequestDto = new CreateUpdateRoomRequestDto();
		final CreateUpdateRoomResponseDto expectedResponse = new CreateUpdateRoomResponseDto(room);
		when(roomService.createRoom(createRoomRequestDto)).thenReturn(expectedResponse);

		//when
		final ResponseEntity<CreateUpdateRoomResponseDto> actualResponse = testedInstance.createRoom(DEPARTMENT_ID,
				createRoomRequestDto);

		//then
		assertEquals(expectedResponse, actualResponse.getBody());
		assertEquals(HttpStatusCode.valueOf(HttpStatus.CREATED.value()), actualResponse.getStatusCode());
	}

	@Test
	void shouldUpdateRoom() {
		//given
		final RoomDto room = mock(RoomDto.class);
		final CreateUpdateRoomRequestDto updateRoomRequestDto = new CreateUpdateRoomRequestDto();
		final CreateUpdateRoomResponseDto expectedResponse = new CreateUpdateRoomResponseDto(room);
		when(roomService.updateRoom(updateRoomRequestDto)).thenReturn(Optional.of(expectedResponse));

		//when
		final ResponseEntity<CreateUpdateRoomResponseDto> actualResponse = testedInstance.updateRoom(DEPARTMENT_ID,
				updateRoomRequestDto);

		//then
		assertEquals(expectedResponse, actualResponse.getBody());
		assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), actualResponse.getStatusCode());
	}
}