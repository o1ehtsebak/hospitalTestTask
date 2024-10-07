package com.department.hospital.component.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.shaded.org.apache.commons.lang3.math.NumberUtils;

import com.department.hospital.component.config.ContainerizedTestEnvironment;
import com.department.hospital.component.department.Department;
import com.department.hospital.component.department.DepartmentRepository;
import com.department.hospital.component.doctor.Doctor;
import com.department.hospital.component.doctor.DoctorRepository;
import com.department.hospital.component.room.Room;
import com.department.hospital.component.room.RoomRepository;
import com.icegreen.greenmail.store.FolderException;

@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientIntegrationTest extends ContainerizedTestEnvironment {

	private static final String DOCTOR_EMAIL = "test@mail.com";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final long ROOM_ID = 5L;
	private static final int TREATMENT_DAYS = 100;
	private static final String DEP_NAME = "depName";

	@LocalServerPort
	private Integer port;
	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private DepartmentRepository departmentRepository;

	@BeforeEach
	public void beforeEach() throws FolderException {
		GREEN_MAIL.purgeEmailFromAllMailboxes();
		testRestTemplate = new TestRestTemplate(new RestTemplateBuilder().rootUri("http://localhost:" + port));
	}

	@Test
	public void shouldRegisterNewPatient() {
		//given
		final long doctorId = createDoctor().getId();
		final long roomId = createRoom().getId();

		final RegisterPatientRequestDto registerPatientRequestDto = new RegisterPatientRequestDto();
		registerPatientRequestDto.setDoctorId(doctorId);
		registerPatientRequestDto.setFirstName(FIRST_NAME);
		registerPatientRequestDto.setLastName(LAST_NAME);
		registerPatientRequestDto.setRoomId(roomId);
		registerPatientRequestDto.setDoctorId(doctorId);
		registerPatientRequestDto.setTreatmentStartDate(LocalDateTime.now());
		registerPatientRequestDto.setTreatmentEndDate(registerPatientRequestDto.getTreatmentStartDate().plusDays(TREATMENT_DAYS));
		var registerPatientRequestDtoHttpEntity = new HttpEntity<>(registerPatientRequestDto);
		final int emailsCount = GREEN_MAIL.getReceivedMessages().length;

		//when
		final ResponseEntity<RegisterPatientResponseDto> registerPatientResponse = testRestTemplate.postForEntity("/patients",
				registerPatientRequestDtoHttpEntity, RegisterPatientResponseDto.class);

		//then
		assertEquals(NumberUtils.INTEGER_ZERO, emailsCount);
		assertEquals(HttpStatus.CREATED, registerPatientResponse.getStatusCode());
		assertNotNull(registerPatientResponse);

		final RegisterPatientResponseDto registerPatientResponseBody = registerPatientResponse.getBody();
		assertNotNull(registerPatientResponseBody);

		final PatientDto registeredPatient = registerPatientResponseBody.registeredPatient();
		assertEquals(FIRST_NAME, registeredPatient.getFirstName());
		assertEquals(LAST_NAME, registeredPatient.getLastName());
		assertEquals(roomId, registeredPatient.getRoomId());
		assertEquals(doctorId, registeredPatient.getDoctorId());

		await().timeout(Duration.ofSeconds(10)).untilAsserted(() -> {
			final int updatedEmailsCount = GREEN_MAIL.getReceivedMessages().length;
			assertEquals(NumberUtils.INTEGER_ONE, updatedEmailsCount);
		});
	}

	private Room createRoom() {
		final Room room = new Room();
		final Department department = new Department();
		department.setName(DEP_NAME);
		departmentRepository.save(department);
		room.setDepartment(department);
		roomRepository.save(room);
		return room;
	}

	private Doctor createDoctor() {
		final Doctor doctor = new Doctor();
		doctor.setEmail(DOCTOR_EMAIL);
		doctorRepository.save(doctor);
		return doctor;
	}

}