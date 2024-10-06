package com.department.hospital.component.doctor.schedule;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.department.hospital.component.doctor.Doctor;
import com.department.hospital.component.doctor.mail.HospitalMailService;
import com.department.hospital.component.doctor.mail.PatientDatesScheduler;
import com.department.hospital.component.patient.Patient;
import com.department.hospital.component.patient.PatientRepository;


@ExtendWith(MockitoExtension.class)
class PatientDatesSchedulerTest {

	private static final String DOC_MAIL = "docMail";

	@Mock
	private HospitalMailService hospitalMailService;
	@Mock
	private PatientRepository patientRepository;

	@InjectMocks
	private PatientDatesScheduler testedInstance;

	@Test
	void shouldProcessPatientsWithOverdueTreatmentEndDate() {
		//given
		final Doctor doctor = new Doctor();
		doctor.setEmail(DOC_MAIL);

		final Patient patient = new Patient();
		patient.setDoctor(doctor);
		final List<Patient> patientList = Collections.singletonList(patient);
		when(patientRepository.findAllByTreatmentEndDateLessThanAndReleasedIsFalse(any())).thenReturn(patientList);

		//when
		testedInstance.checkPatientTreatmentEndDates();

		//then
		assertTrue(patient.isReleased());
	}
}