package com.department.hospital.component.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.department.hospital.component.doctor.Doctor;
import com.department.hospital.component.doctor.DoctorRepository;
import com.department.hospital.component.doctor.mail.HospitalMailService;
import com.department.hospital.component.room.Room;
import com.department.hospital.component.room.RoomRepository;


@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

	private static final int ROOM_NUMBER = 50;
	private static final long DOCTOR_ID = 1L;
	private static final long ROOM_ID = 2L;
	private static final String DOC_MAIL = "doc mail";
	private static final long PATIENT_ID = 1L;
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";

	@Mock
	private PatientRepository patientRepository;
	@Mock
	private RoomRepository roomRepository;
	@Mock
	private DoctorRepository doctorRepository;
	@Mock
	private HospitalMailService hospitalMailService;
	@Mock
	private PatientMapper patientMapper;

	@InjectMocks
	private PatientService testedInstance;

	@Test
	void shouldRegisterNewPatient() {
		//give
		final RegisterPatientRequestDto registerPatientRequestDto = new RegisterPatientRequestDto();
		registerPatientRequestDto.setDoctorId(DOCTOR_ID);
		registerPatientRequestDto.setRoomId(ROOM_ID);

		final Doctor doctor = new Doctor();
		doctor.setEmail(DOC_MAIL);
		final Room room = new Room();
		room.setNumber(ROOM_NUMBER);
		when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(doctor));
		when(roomRepository.findById(ROOM_ID)).thenReturn(Optional.of(room));

		final Patient newPatient = new Patient();
		newPatient.setId(PATIENT_ID);
		newPatient.setFirstName(FIRST_NAME);
		newPatient.setLastName(LAST_NAME);
		when(patientMapper.registerPatientRequestDtoToPatient(registerPatientRequestDto)).thenReturn(newPatient);
		final PatientDto expectedPatientDto = new PatientDto();
		when(patientMapper.patientToRegisterPatientResponseDto(newPatient)).thenReturn(expectedPatientDto);

		//when
		final RegisterPatientResponseDto actualRegisterPatientResponseDto = testedInstance.registerPatient(registerPatientRequestDto);

		//then
		assertEquals(room, newPatient.getRoom());
		assertEquals(doctor, newPatient.getDoctor());
		assertEquals(expectedPatientDto, actualRegisterPatientResponseDto.registeredPatient());
		verify(hospitalMailService).sendNewPatientMsg(DOC_MAIL, PATIENT_ID, FIRST_NAME, LAST_NAME, ROOM_NUMBER);
	}
}