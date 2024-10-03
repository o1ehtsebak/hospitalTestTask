package com.department.hospital.service.mail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.department.hospital.component.doctor.mail.HospitalMailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@ExtendWith(MockitoExtension.class)
class HospitalMailServiceTest {

	private static final long PATIENT_ID = 1L;
	private static final String TO_MAIL = "toMail";
	private static final String TEST_FIRST_NAME = "testFirstName";
	private static final String TEST_LAST_NAME = "testLastName";

	@Mock
	private JavaMailSender mailSender;

	@InjectMocks
	private HospitalMailService testedInstance;

	@Test
	void shouldSendMsg() {
		//given

		//when
		testedInstance.sendTreatmentEndDateMsg(TO_MAIL, PATIENT_ID, TEST_FIRST_NAME, TEST_LAST_NAME);

		//then
		verify(mailSender).send(any(SimpleMailMessage.class));
	}
}