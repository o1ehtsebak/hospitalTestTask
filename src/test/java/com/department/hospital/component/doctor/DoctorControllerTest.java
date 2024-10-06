package com.department.hospital.component.doctor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
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
class DoctorControllerTest {

	private static final long DOCTOR_ID = 1L;
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	@Mock
	DoctorService doctorService;

	@InjectMocks
	DoctorController testedInstance;

	@Test
	void shouldGetDoctor() {
		//given
		final DoctorDto expectedDoctor = mock(DoctorDto.class);
		when(doctorService.findDoctor(DOCTOR_ID)).thenReturn(Optional.of(expectedDoctor));

		//when
		final ResponseEntity<DoctorDto> doctorResponse = testedInstance.getDoctor(DOCTOR_ID);

		//then
		assertEquals(expectedDoctor, doctorResponse.getBody());
	}

	@Test
	void shouldNotGetDoctor() {
		//given
		when(doctorService.findDoctor(DOCTOR_ID)).thenReturn(Optional.empty());

		//when
		final ResponseEntity<DoctorDto> doctorResponse = testedInstance.getDoctor(DOCTOR_ID);

		//then
		assertEquals(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), doctorResponse.getStatusCode());
	}

	@Test
	void shouldCreateNewDoctor() {
		//given
		final DoctorRequestDto doctorRequestDto = new DoctorRequestDto(FIRST_NAME, LAST_NAME, List.of());
		final DoctorDto doctorDto = mock(DoctorDto.class);
		final DoctorResponseDto expectedDoctorResponse = new DoctorResponseDto(doctorDto);
		when(doctorService.createDoctor(doctorRequestDto)).thenReturn(expectedDoctorResponse);

		//when
		final ResponseEntity<DoctorResponseDto> newDoctor = testedInstance.createDoctor(doctorRequestDto);

		//then
		assertEquals(expectedDoctorResponse, newDoctor.getBody());
		assertEquals(HttpStatusCode.valueOf(HttpStatus.CREATED.value()), newDoctor.getStatusCode());
	}

	@Test
	void shouldUpdateDoctor() {
		//given
		final DoctorRequestDto doctorRequestDto = new DoctorRequestDto(FIRST_NAME, LAST_NAME, List.of());
		final DoctorDto doctorDto = mock(DoctorDto.class);
		final DoctorResponseDto expectedDoctorResponse = new DoctorResponseDto(doctorDto);
		when(doctorService.updateDoctor(DOCTOR_ID, doctorRequestDto)).thenReturn(Optional.of(expectedDoctorResponse));

		//when
		final ResponseEntity<DoctorResponseDto> updatedDoctor = testedInstance.updateDoctor(DOCTOR_ID, doctorRequestDto);

		//then
		assertEquals(expectedDoctorResponse, updatedDoctor.getBody());
		assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), updatedDoctor.getStatusCode());
	}

	@Test
	void shouldNotUpdateDoctorIfItDoesNotExist() {
		//given
		final DoctorRequestDto doctorRequestDto = new DoctorRequestDto(FIRST_NAME, LAST_NAME, List.of());
		when(doctorService.updateDoctor(DOCTOR_ID, doctorRequestDto)).thenReturn(Optional.empty());

		//when
		final ResponseEntity<DoctorResponseDto> updatedDoctor = testedInstance.updateDoctor(DOCTOR_ID, doctorRequestDto);

		//then
		assertEquals(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), updatedDoctor.getStatusCode());
	}
}