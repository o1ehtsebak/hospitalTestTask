package com.department.hospital.component.patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

	@Mock
	PatientService patientService;

	@InjectMocks
	PatientController patientController;

	@Test
	void shouldRegisterNewPatient() {
		//given
		final RegisterPatientRequestDto registerPatientRequestDto = new RegisterPatientRequestDto();
		final RegisterPatientResponseDto expectedResponse = new RegisterPatientResponseDto(new PatientDto());
		when(patientService.registerPatient(registerPatientRequestDto)).thenReturn(expectedResponse);

		//when
		final ResponseEntity<RegisterPatientResponseDto> registerPatientResponseDtoResponseEntity = patientController.registerPatient(registerPatientRequestDto);

		//then
		assertEquals(expectedResponse, registerPatientResponseDtoResponseEntity.getBody());
		assertEquals(HttpStatusCode.valueOf(HttpStatus.CREATED.value()), registerPatientResponseDtoResponseEntity.getStatusCode());
	}
}