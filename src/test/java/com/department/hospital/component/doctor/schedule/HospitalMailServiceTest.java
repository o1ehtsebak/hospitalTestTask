package com.department.hospital.component.doctor.schedule;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.department.hospital.component.doctor.mail.HospitalMailService;


@ExtendWith(MockitoExtension.class)
class HospitalMailServiceTest {

	private static final long PATIENT_ID = 1L;
	private static final String TO_MAIL = "toMail";
	private static final String TEST_FIRST_NAME = "testFirstName";
	private static final String TEST_LAST_NAME = "testLastName";

	@Mock
	private JavaMailSender mailSender;

	@Captor
	private ArgumentCaptor<SimpleMailMessage> mailMessageArgumentCaptor;

	@InjectMocks
	private HospitalMailService testedInstance;

	@Test
	void shouldSendMsg() {
		//given

		//when
		testedInstance.sendTreatmentEndDateMsg(TO_MAIL, PATIENT_ID, TEST_FIRST_NAME, TEST_LAST_NAME);

		//then
		verify(mailSender).send(mailMessageArgumentCaptor.capture());

		final SimpleMailMessage mailMessage = mailMessageArgumentCaptor.getValue();

		assertNotNull(mailMessage.getTo());
		assertNotNull(mailMessage.getSubject());
		assertNotNull(mailMessage.getText());
	}
}