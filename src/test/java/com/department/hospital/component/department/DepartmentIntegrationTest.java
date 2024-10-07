package com.department.hospital.component.department;

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


@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentIntegrationTest extends ContainerizedTestEnvironment {

	private static final String DEPARTMENT_NAME = "Surgery";
	private static final String NEW_DEPARTMENT_NAME = "newSurgery";

	@LocalServerPort
	private Integer port;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private TestRestTemplate testRestTemplate;

	@BeforeEach
	public void beforeEach() {
		departmentRepository.deleteAll();
		testRestTemplate = new TestRestTemplate(new RestTemplateBuilder().rootUri("http://localhost:" + port));
	}

	@Test
	public void shouldNotFindDepartment() {
		//given

		//when
		final ResponseEntity<DepartmentDto> departmentDtoResponse = testRestTemplate.getForEntity("/departments?name=Surgery",
				DepartmentDto.class);

		//then
		assertEquals(HttpStatus.NOT_FOUND, departmentDtoResponse.getStatusCode());
	}

	@Test
	public void shouldFindDepartment() {
		//given
		final Department surgeryDepartment = new Department();
		surgeryDepartment.setName(DEPARTMENT_NAME);
		departmentRepository.save(surgeryDepartment);

		//when
		final ResponseEntity<DepartmentDto> departmentDtoResponse = testRestTemplate
				.getForEntity(String.format("/departments?name=%s", DEPARTMENT_NAME), DepartmentDto.class);

		//then
		assertEquals(HttpStatus.OK, departmentDtoResponse.getStatusCode());

		final DepartmentDto responseBody = departmentDtoResponse.getBody();
		assertNotNull(responseBody);
		assertEquals(surgeryDepartment.getName(), responseBody.name());
	}

	@Test
	void shouldCreateDepartment() {
		//given
		final CreateDepartmentRequestDto createDepartmentRequestDto = new CreateDepartmentRequestDto(DEPARTMENT_NAME);

		//when
		final ResponseEntity<CreateUpdateDepartmentResponseDto> departmentCreatedResponse = testRestTemplate
				.postForEntity("/departments", createDepartmentRequestDto, CreateUpdateDepartmentResponseDto.class);

		//then
		assertEquals(HttpStatus.CREATED, departmentCreatedResponse.getStatusCode());
		final CreateUpdateDepartmentResponseDto departmentCreatedResponseBody = departmentCreatedResponse.getBody();
		assertNotNull(departmentCreatedResponseBody);
		assertEquals(DEPARTMENT_NAME, departmentCreatedResponseBody.name());
	}

	@Test
	void shouldUpdateDepartment() {
		//given
		final Department existingDepartment = new Department();
		existingDepartment.setName(DEPARTMENT_NAME);
		departmentRepository.save(existingDepartment);
		final long existingDepartmentId = existingDepartment.getId();

		final UpdateDepartmentRequestDto updateDepartmentRequestDto = new UpdateDepartmentRequestDto();
		updateDepartmentRequestDto.setName(NEW_DEPARTMENT_NAME);
		var updateDepartmentHttpEntity = new HttpEntity<>(updateDepartmentRequestDto);

		//when
		var updateDepartmentResponse = testRestTemplate.exchange("/departments/{departmentId}", HttpMethod.PUT,
				updateDepartmentHttpEntity, CreateUpdateDepartmentResponseDto.class, existingDepartmentId);

		//then
		assertEquals(HttpStatus.OK, updateDepartmentResponse.getStatusCode());

		final CreateUpdateDepartmentResponseDto updateDepartmentResponseBody = updateDepartmentResponse.getBody();
		assertNotNull(updateDepartmentResponseBody);
		assertEquals(existingDepartmentId, updateDepartmentResponseBody.id());
		assertEquals(NEW_DEPARTMENT_NAME, updateDepartmentResponseBody.name());
	}

	@Test
	public void shouldGetDepartmentLoad() {
		//given
		final Department surgeryDepartment = new Department();
		surgeryDepartment.setName(DEPARTMENT_NAME);
		departmentRepository.save(surgeryDepartment);

		//when
		final ResponseEntity<DepartmentLoadDto> departmentDtoResponse = testRestTemplate
				.getForEntity(String.format("/departments/load?id=%s", surgeryDepartment.getId()), DepartmentLoadDto.class);

		//then
		assertEquals(HttpStatus.OK, departmentDtoResponse.getStatusCode());

		final DepartmentLoadDto responseBody = departmentDtoResponse.getBody();
		assertNotNull(responseBody);
		assertEquals(surgeryDepartment.getId(), responseBody.id());
		assertEquals(surgeryDepartment.getName(), responseBody.name());
	}

}
