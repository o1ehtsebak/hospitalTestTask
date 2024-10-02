package com.department.hospital.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class HospitalMailService {

	private static final String PATIENT_RELEASED = "Patient Released";
	private static final String NEW_PATIENT = "New Patient";
	private static final String RELEASE_MSG_FORMAT = "Hello. Patient ID - %s, name - %s %s was released from hospital.";
	private static final String NEW_PATIENT_MSG_FORMAT = "Hello. You have new patient. Patient ID - %s, name - %s %s is. Room - %s";

	private final JavaMailSender mailSender;

	@Async
	public void sendTreatmentEndDateMsg(String toMail, Long patientId, String firstName, String lastName) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toMail);
		message.setSubject(PATIENT_RELEASED);
		message.setText(String.format(RELEASE_MSG_FORMAT, patientId, firstName, lastName));
		mailSender.send(message);
	}

	@Async
	public void sendNewPatientMsg(String toMail, Long patientId, String firstName, String lastName, Integer roomRumber) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toMail);
		message.setSubject(NEW_PATIENT);
		message.setText(String.format(NEW_PATIENT_MSG_FORMAT, patientId, firstName, lastName, roomRumber));
		mailSender.send(message);
	}
}
