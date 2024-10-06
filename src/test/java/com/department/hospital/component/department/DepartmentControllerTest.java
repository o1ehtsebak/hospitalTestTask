package com.department.hospital.component.department;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;


@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

	private static final String DEPARTMENT_NAME = "departmentName";
	private static final long DEPARTMENT_ID = 1L;

	@Mock
	DepartmentService departmentService;

	@InjectMocks
	DepartmentController testedInstance;

	@Test
	void shouldGetDepartmentByName() {
		//given
		final DepartmentDto departmentDto = new DepartmentDto(DEPARTMENT_ID, DEPARTMENT_NAME, List.of(), List.of());
		when(departmentService.findDepartmentByName(DEPARTMENT_NAME)).thenReturn(Optional.of(departmentDto));

		//when
		final ResponseEntity<DepartmentDto> departmentResponse = testedInstance.getDepartment(DEPARTMENT_NAME);

		//then
		assertEquals(departmentDto, departmentResponse.getBody());
	}

	@Test
	void shouldNotGetDepartmentByName() {
		//given
		when(departmentService.findDepartmentByName(DEPARTMENT_NAME)).thenReturn(Optional.empty());

		//when
		final ResponseEntity<DepartmentDto> departmentResponse = testedInstance.getDepartment(DEPARTMENT_NAME);

		//then
		assertEquals(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), departmentResponse.getStatusCode());
	}

	@Test
	void shouldGetDepartmentLoadInfo() {
		//given
		final DepartmentLoadDto expectedDepartmentLoadDto = new DepartmentLoadDto(DEPARTMENT_ID, DEPARTMENT_NAME, List.of());
		when(departmentService.getDepartmentLoadInfo(DEPARTMENT_ID)).thenReturn(expectedDepartmentLoadDto);

		//when
		final DepartmentLoadDto actualDepartmentLoadDto = testedInstance.getDepartmentLoadInfo(DEPARTMENT_ID);

		//then
		assertEquals(expectedDepartmentLoadDto, actualDepartmentLoadDto);
	}

	@Test
	void shouldCreateDepartment() {
		//given
		final CreateUpdateDepartmentResponseDto expectedResponse = new CreateUpdateDepartmentResponseDto(DEPARTMENT_ID,
				DEPARTMENT_NAME);
		final CreateDepartmentRequestDto createDepartmentRequestDto = new CreateDepartmentRequestDto(DEPARTMENT_NAME);
		when(departmentService.createDepartment(createDepartmentRequestDto)).thenReturn(expectedResponse);

		//when
		final ResponseEntity<CreateUpdateDepartmentResponseDto> createDepartmentResponse = testedInstance
				.createDepartment(createDepartmentRequestDto);

		//then
		assertEquals(expectedResponse, createDepartmentResponse.getBody());
		assertEquals(HttpStatusCode.valueOf(HttpStatus.CREATED.value()), createDepartmentResponse.getStatusCode());
	}

	@Test
	void shouldUpdateDepartment() {
		//given
		final UpdateDepartmentRequestDto updateDepartmentRequestDto = new UpdateDepartmentRequestDto();
		final CreateUpdateDepartmentResponseDto expectedResponse = new CreateUpdateDepartmentResponseDto(DEPARTMENT_ID,
				DEPARTMENT_NAME);
		when(departmentService.updateDepartment(updateDepartmentRequestDto)).thenReturn(Optional.of(expectedResponse));

		//when
		final ResponseEntity<CreateUpdateDepartmentResponseDto> updateDepartmentResponse = testedInstance
				.updateDepartment(DEPARTMENT_ID, updateDepartmentRequestDto);

		//then
		assertEquals(expectedResponse, updateDepartmentResponse.getBody());
		assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), updateDepartmentResponse.getStatusCode());
	}

	@Test
	void shouldNotUpdateDepartmentIfItDoesNotExist() {
		//given
		final UpdateDepartmentRequestDto updateDepartmentRequestDto = new UpdateDepartmentRequestDto();
		when(departmentService.updateDepartment(updateDepartmentRequestDto)).thenReturn(Optional.empty());

		//when
		final ResponseEntity<CreateUpdateDepartmentResponseDto> updateDepartmentResponse = testedInstance
				.updateDepartment(DEPARTMENT_ID, updateDepartmentRequestDto);

		//then
		assertEquals(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), updateDepartmentResponse.getStatusCode());
	}
}