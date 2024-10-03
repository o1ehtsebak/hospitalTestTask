package com.department.hospital.service.schedule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import com.department.hospital.component.doctor.mail.PatientDatesScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.department.hospital.component.doctor.Doctor;
import com.department.hospital.component.patient.Patient;
import com.department.hospital.component.patient.PatientRepository;
import com.department.hospital.component.doctor.mail.HospitalMailService;


@ExtendWith(MockitoExtension.class)
class PatientDatesSchedulerTest {

	private static final long PATIENT_ID = 1L;
	private static final String DOC_MAIL = "docMail";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";

	@Mock
	private HospitalMailService hospitalMailService;
	@Mock
	private PatientRepository patientRepository;

	@InjectMocks
	private PatientDatesScheduler testedInstance;

	@Test
	void shouldProcessPatientsWithOverdueTreatmentEndDate() {
		//given
		final Doctor doctor = mock(Doctor.class);
		when(doctor.getEmail()).thenReturn(DOC_MAIL);

		final Patient patient = mock(Patient.class);
		when(patient.getDoctor()).thenReturn(doctor);
		when(patient.getId()).thenReturn(PATIENT_ID);
		when(patient.getFirstName()).thenReturn(FIRST_NAME);
		when(patient.getLastName()).thenReturn(LAST_NAME);
		final List<Patient> patientList = Collections.singletonList(patient);
		when(patientRepository.findAllByTreatmentEndDateLessThanAndReleasedIsFalse(any())).thenReturn(patientList);

		//when
		testedInstance.checkPatientTreatmentEndDates();

		//then
		verify(patient).setDoctor(null);
		verify(patient).setRoom(null);
		verify(patientRepository).saveAll(patientList);
		verify(hospitalMailService).sendTreatmentEndDateMsg(DOC_MAIL, PATIENT_ID, FIRST_NAME, LAST_NAME);
	}
}