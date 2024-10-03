package com.department.hospital.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.department.hospital.component.doctor.Doctor;
import com.department.hospital.component.doctor.DoctorsRepository;
import com.department.hospital.component.patient.*;
import com.department.hospital.component.room.Room;
import com.department.hospital.component.room.RoomsRepository;
import com.department.hospital.component.patient.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.department.hospital.component.patient.PatientMapper;
import com.department.hospital.component.doctor.mail.HospitalMailService;


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
	private PatientService testedInstance;

	private RegisterPatientRequestDto registerPatientRequestDto;

	@BeforeEach
	void setUp() {
		this.registerPatientRequestDto = new RegisterPatientRequestDto();
		this.registerPatientRequestDto.setDoctorId(DOCTOR_ID);
		this.registerPatientRequestDto.setRoomId(ROOM_ID);
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
		when(patientMapper.registerPatientRequestDtoToPatient(registerPatientRequestDto)).thenReturn(registeredPatient);

		//when
		final RegisterPatientResponseDto actualRegisterPatientResponseDto = testedInstance.registerPatient(registerPatientRequestDto);

		//then
		verify(registeredPatient).setDoctor(doctor);
		verify(registeredPatient).setRoom(room);
		verify(patientRepository).save(registeredPatient);
		verify(hospitalMailService).sendNewPatientMsg(eq(DOC_MAIL), anyLong(), any(), any(), eq(ROOM_NUMBER));
	}
}