package com.department.hospital.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.department.hospital.dto.DepartmentDto;
import com.department.hospital.entity.Department;
import com.department.hospital.repository.DepartmentsRepository;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentsIntegrationTest {

	private static final int EXPECTED_DEPARTMENTS_COUNT = 4;
	private static final String DEP_NAME_1 = "testDep1";
	private static final String DEP_NAME_2 = "testDep2";
	private static final String DEP_NAME_3 = "testDep3";

	@Container
	static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0-debian"));

	@Autowired
	private DepartmentsRepository departmentsRepository;
	@LocalServerPort
	private Integer port;
	@Autowired
	private TestRestTemplate restTemplate;

	@DynamicPropertySource
	static void kafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", () -> mySQLContainer.getJdbcUrl());
		registry.add("spring.datasource.driverClassName", () -> mySQLContainer.getDriverClassName());
		registry.add("spring.datasource.username", () -> mySQLContainer.getUsername());
		registry.add("spring.datasource.password", () -> mySQLContainer.getPassword());
		registry.add("spring.flyway.enabled", () -> "true");
	}

	@AfterAll
	static void afterAll() {
		mySQLContainer.stop();
	}

	@BeforeEach
	public void setUp() {
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:" + port));
	}

	@Test
	void testMySQLContainerIsRunning() {
		assertTrue(mySQLContainer.isRunning());
	}

	@Test
	public void shouldGetAllDepartments() {
		final List<Department> allDepartments = departmentsRepository.findAll();

		assertEquals(EXPECTED_DEPARTMENTS_COUNT, allDepartments.size());
	}

	@Test
	void shouldGetExistingDepartment() {
		final Department dep1 = new Department();
		dep1.setName(DEP_NAME_1);

		departmentsRepository.saveAll(List.of(dep1));

		ResponseEntity<DepartmentDto> response = restTemplate.getForEntity(String.format("/departments?name=%s", DEP_NAME_1),
				DepartmentDto.class);

		Assertions.assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(DEP_NAME_1, response.getBody().name());
	}
}
