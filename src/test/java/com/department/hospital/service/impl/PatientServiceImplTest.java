package com.department.hospital.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.department.hospital.dto.PatientDto;
import com.department.hospital.dto.request.RegisterPatientDto;
import com.department.hospital.entity.*;
import com.department.hospital.mapper.PatientMapper;
import com.department.hospital.repository.*;
import com.department.hospital.service.mail.HospitalMailService;


@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

	private static final int ROOM_NUMBER = 50;
	private static final long DOCTOR_ID = 1L;
	private static final long ROOM_ID = 2L;
	private static final String DOC_MAIL = "doc mail";
	private static final String NEW_PATIENT_FIRST_NAME = "new patient first name";
	private static final String NEW_PATIENT_LAST_NAME = "new patient last name";

	@Mock
	private PatientRepository patientRepository;
	@Mock
	private RoomsRepository roomsRepository;
	@Mock
	private DoctorsRepository doctorsRepository;
	@Mock
	private HospitalMailService hospitalMailService;
	@Mock
	private PatientMapper patientMapper;

	@InjectMocks
	private PatientServiceImpl testedInstance;

	private RegisterPatientDto registerPatientDto;

	@BeforeEach
	void setUp() {
		this.registerPatientDto = new RegisterPatientDto();
		this.registerPatientDto.setDoctorId(DOCTOR_ID);
		this.registerPatientDto.setRoomId(ROOM_ID);
	}

	@Test
	void shouldRegisterNewPatient() {
		//give
		final Doctor doctor = mock(Doctor.class);
		when(doctor.getEmail()).thenReturn(DOC_MAIL);
		final Room room = mock(Room.class);
		when(room.getNumber()).thenReturn(ROOM_NUMBER);
		when(doctorsRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(doctor));
		when(roomsRepository.findById(ROOM_ID)).thenReturn(Optional.of(room));
		final Patient registeredPatient = mock(Patient.class);
		when(patientMapper.patientDtoToPatient(registerPatientDto)).thenReturn(registeredPatient);
		final PatientDto expectedPatiendDto = new PatientDto();
		when(patientMapper.patientToPatientDto(registeredPatient)).thenReturn(expectedPatiendDto);

		//when
		final PatientDto actualPatientDto = testedInstance.registerPatient(registerPatientDto);

		//then
		assertEquals(expectedPatiendDto, actualPatientDto);

		verify(registeredPatient).setDoctor(doctor);
		verify(registeredPatient).setRoom(room);
		verify(patientRepository).save(registeredPatient);
		verify(hospitalMailService).sendNewPatientMsg(eq(DOC_MAIL), anyLong(), any(), any(), eq(ROOM_NUMBER));
	}
}