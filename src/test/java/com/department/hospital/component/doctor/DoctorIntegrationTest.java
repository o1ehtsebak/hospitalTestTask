package com.department.hospital.component.doctor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import com.department.hospital.component.config.ContainerizedTestEnvironment;
import com.department.hospital.component.department.Department;
import com.department.hospital.component.department.DepartmentRepository;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorIntegrationTest extends ContainerizedTestEnvironment {

	private static final String DOCTOR_FIRST_NAME = "firstName";
	private static final String DOCTOR_LAST_NAME = "lastName";

	@LocalServerPort
	private Integer port;
	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private DepartmentRepository departmentRepository;

	@BeforeEach
	public void beforeEach() {
		doctorRepository.deleteAll();
		testRestTemplate = new TestRestTemplate(new RestTemplateBuilder().rootUri("http://localhost:" + port));
	}

	@Test
	public void shouldNotFindDoctor() {
		//given

		//when
		final ResponseEntity<DoctorDto> doctorDtoResponseEntity = testRestTemplate.getForEntity("/doctors?id=1", DoctorDto.class);

		//then
		assertEquals(HttpStatus.NOT_FOUND, doctorDtoResponseEntity.getStatusCode());
	}

	@Test
	public void shouldFindDoctor() {
		//given
		final Doctor doctor = new Doctor();
		doctorRepository.save(doctor);
		final long doctorId = doctor.getId();

		//when
		final ResponseEntity<DoctorDto> doctorDtoResponseEntity = testRestTemplate
				.getForEntity(String.format("/doctors?id=%s", doctorId), DoctorDto.class);

		//then
		assertEquals(HttpStatus.OK, doctorDtoResponseEntity.getStatusCode());

		final DoctorDto responseBody = doctorDtoResponseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(doctorId, responseBody.id());
	}

	@Test
	void shouldCreateDepartment() {
		//given
		final DoctorRequestDto createDepartmentRequestDto = new DoctorRequestDto(DOCTOR_FIRST_NAME, DOCTOR_LAST_NAME, List.of());

		//when
		final ResponseEntity<DoctorResponseDto> doctorResponseDtoResponseEntity = testRestTemplate.postForEntity("/doctors",
				createDepartmentRequestDto, DoctorResponseDto.class);

		//then
		assertEquals(HttpStatus.CREATED, doctorResponseDtoResponseEntity.getStatusCode());

		final DoctorResponseDto doctorResponseDto = doctorResponseDtoResponseEntity.getBody();
		assertNotNull(doctorResponseDto);
		final DoctorDto createdDoctor = doctorResponseDto.doctor();
		assertEquals(DOCTOR_FIRST_NAME, createdDoctor.firstName());
		assertEquals(DOCTOR_LAST_NAME, createdDoctor.lastName());
	}

	@Test
	void shouldUpdateDoctor() {
		//given
		final Doctor doctor = new Doctor();
		doctor.setFirstName(DOCTOR_FIRST_NAME);
		doctorRepository.save(doctor);
		final long doctorId = doctor.getId();

		final Department department = new Department();
		departmentRepository.save(department);
		final long existingDepartmentId = department.getId();

		final DoctorRequestDto doctorRequestDto = new DoctorRequestDto(DOCTOR_FIRST_NAME, DOCTOR_LAST_NAME,
				List.of(existingDepartmentId));
		var doctorRequestDtoHttpEntity = new HttpEntity<>(doctorRequestDto);

		//when
		var doctorResponseDtoResponseEntity = testRestTemplate.exchange("/doctors/{doctorId}", HttpMethod.PUT,
				doctorRequestDtoHttpEntity, DoctorResponseDto.class, doctorId);

		//then
		assertEquals(HttpStatus.OK, doctorResponseDtoResponseEntity.getStatusCode());

		final DoctorResponseDto doctorResponseDto = doctorResponseDtoResponseEntity.getBody();
		assertNotNull(doctorResponseDto);
		final DoctorDto doctorDto = doctorResponseDto.doctor();
		assertNotNull(doctorDto);
	}
}
