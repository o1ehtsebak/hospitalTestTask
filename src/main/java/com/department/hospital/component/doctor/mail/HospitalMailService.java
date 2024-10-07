package com.department.hospital.component.doctor.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class HospitalMailService {

	private static final String PATIENT_RELEASED = "Patient Released";
	private static final String NEW_PATIENT = "New Patient";
	private static final String RELEASE_MSG_FORMAT = "Hello. Patient ID - %s, name - %s %s was released from hospital.";
	private static final String NEW_PATIENT_MSG_FORMAT = "Hello. You have new patient. Patient ID - %s, name - %s %s is. Room - %s";

	private final JavaMailSender mailSender;

	@Async
	public void sendTreatmentEndDateMsg(String toMail, Long patientId, String firstName, String lastName) {
		sendMsg(toMail, PATIENT_RELEASED, String.format(RELEASE_MSG_FORMAT, patientId, firstName, lastName));
	}

	@Async
	public void sendNewPatientMsg(String toMail, Long patientId, String firstName, String lastName, Integer roomNumber) {
		sendMsg(toMail, NEW_PATIENT, String.format(NEW_PATIENT_MSG_FORMAT, patientId, firstName, lastName, roomNumber));
	}

	private void sendMsg(String toMail, String patientReleased, String mailText) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toMail);
		message.setSubject(patientReleased);
		message.setText(mailText);
		mailSender.send(message);
	}
}
