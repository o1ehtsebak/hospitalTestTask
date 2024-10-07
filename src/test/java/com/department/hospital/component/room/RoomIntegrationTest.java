package com.department.hospital.component.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import com.department.hospital.component.config.ContainerizedTestEnvironment;
import com.department.hospital.component.department.Department;
import com.department.hospital.component.department.DepartmentRepository;
import com.department.hospital.component.doctor.DoctorRepository;


@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomIntegrationTest extends ContainerizedTestEnvironment {

	private static final String DOCTOR_FIRST_NAME = "firstName";
	private static final String DOCTOR_LAST_NAME = "lastName";
	private static final int ROOM_NUMBER = 105;
	private static final int NUMBER_OF_PLACES = 20;
	private static final int NEW_ROOM_NUMBER = 109;

	@LocalServerPort
	private Integer port;
	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private RoomRepository roomRepository;

	private Department department;
	private long departmentId;

	@BeforeEach
	public void beforeEach() {
		this.department = new Department();
		departmentRepository.save(department);
		this.departmentId = department.getId();

		roomRepository.deleteAll();

		testRestTemplate = new TestRestTemplate(new RestTemplateBuilder().rootUri("http://localhost:" + port));
	}

	@Test
	public void shouldNotFindRoom() {
		//given

		//when
		final ResponseEntity<RoomDto> roomDtoResponse = testRestTemplate.getForEntity("/departments/1/rooms?roomNumber=1",
				RoomDto.class);

		//then
		assertEquals(HttpStatus.NOT_FOUND, roomDtoResponse.getStatusCode());
	}

	@Test
	public void shouldFindRoom() {
		//given
		final Room room = new Room();
		room.setDepartment(department);
		room.setNumber(ROOM_NUMBER);
		roomRepository.save(room);

		//when
		final ResponseEntity<RoomDto> roomDtoResponse = testRestTemplate.getForEntity(
				String.format("/departments/{departmentId}/rooms?roomNumber=%s", ROOM_NUMBER), RoomDto.class, departmentId);

		//then
		assertEquals(HttpStatus.OK, roomDtoResponse.getStatusCode());

		final RoomDto responseBody = roomDtoResponse.getBody();
		assertNotNull(responseBody);
		assertEquals(ROOM_NUMBER, responseBody.number());
	}

	@Test
	void shouldCreateRoom() {
		//given
		final CreateUpdateRoomRequestDto createRoomRequestDto = new CreateUpdateRoomRequestDto();
		createRoomRequestDto.setDepartmentId(departmentId);
		createRoomRequestDto.setNumberOfPlaces(NUMBER_OF_PLACES);
		createRoomRequestDto.setNumber(ROOM_NUMBER);

		//when
		final ResponseEntity<CreateUpdateRoomResponseDto> createRoomResponseEntity = testRestTemplate.postForEntity(
				"/departments/{departmentId}/rooms", createRoomRequestDto, CreateUpdateRoomResponseDto.class, departmentId);

		//then
		assertEquals(HttpStatus.CREATED, createRoomResponseEntity.getStatusCode());

		final CreateUpdateRoomResponseDto createRoomBody = createRoomResponseEntity.getBody();
		assertNotNull(createRoomBody);
		final RoomDto createdRoom = createRoomBody.room();
		assertEquals(ROOM_NUMBER, createdRoom.number());
		assertEquals(NUMBER_OF_PLACES, createdRoom.numberOfPlaces());
	}

	@Test
	void shouldUpdateRoom() {
		//given
		final Room room = new Room();
		room.setDepartment(department);
		room.setNumber(ROOM_NUMBER);
		roomRepository.save(room);

		final CreateUpdateRoomRequestDto updateRoomRequestDto = new CreateUpdateRoomRequestDto();
		updateRoomRequestDto.setOriginalNumber(ROOM_NUMBER);
		updateRoomRequestDto.setDepartmentId(departmentId);
		updateRoomRequestDto.setNumberOfPlaces(NUMBER_OF_PLACES);
		updateRoomRequestDto.setNumber(NEW_ROOM_NUMBER);

		var updateRoomRequestDtoHttpEntity = new HttpEntity<>(updateRoomRequestDto);

		//when
		var updateRoomResponseEntity = testRestTemplate.exchange("/departments/{departmentId}/rooms", HttpMethod.PUT,
				updateRoomRequestDtoHttpEntity, CreateUpdateRoomResponseDto.class, departmentId);

		//then
		assertEquals(HttpStatus.OK, updateRoomResponseEntity.getStatusCode());

		final CreateUpdateRoomResponseDto updateRoomResponseDto = updateRoomResponseEntity.getBody();
		assertNotNull(updateRoomResponseDto);
		final RoomDto roomDto = updateRoomResponseDto.room();
		assertNotNull(roomDto);
		assertEquals(NEW_ROOM_NUMBER, roomDto.number());
		assertEquals(NUMBER_OF_PLACES, roomDto.numberOfPlaces());
	}
}
