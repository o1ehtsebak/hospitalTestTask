package com.department.hospital.component.doctor.mail;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.department.hospital.component.doctor.Doctor;
import com.department.hospital.component.patient.Patient;
import com.department.hospital.component.patient.PatientRepository;
import com.department.hospital.component.doctor.mail.HospitalMailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
@Component
public class PatientDatesScheduler {

	private final HospitalMailService hospitalMailService;
	private final PatientRepository patientRepository;

	// Cron for local testing - every minute
	//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 0 0 * * *") // every day at midnight
	public void checkPatientTreatmentEndDates() {
		LOGGER.info("Start processing patients treatment days");

		final List<Patient> patientsToBeDischarged = patientRepository
				.findAllByTreatmentEndDateLessThanAndReleasedIsFalse(LocalDateTime.now());
		releasePatients(patientsToBeDischarged);
	}

	private void releasePatients(List<Patient> patients) {
		if (isNotEmpty(patients)) {
			patients.forEach(this::processPatientRelease);
			patientRepository.saveAll(patients);
		}
	}

	private void processPatientRelease(Patient patient) {
		final Doctor doctor = patient.getDoctor();
		LOGGER.info("Doc {} have released patient", doctor.getFirstName());

		hospitalMailService.sendTreatmentEndDateMsg(doctor.getEmail(), patient.getId(), patient.getFirstName(),
				patient.getLastName());

		patient.setDoctor(null);
		patient.setRoom(null);
		patient.setReleased(true);
	}
}
