package com.department.hospital.component.recommendation;

import java.util.List;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.department.hospital.component.patient.Patient;
import com.department.hospital.component.patient.PatientRepository;

import lombok.RequiredArgsConstructor;


@Service
@Profile("!test")
@RequiredArgsConstructor
public class RecommendationService {

	private static final int BATCH_SIZE = 2;
	private static final int FIRST_PAGE = 0;

	private final VectorStore vectorStore;
	private final PatientRepository patientRepository;

	public void loadDocuments() {
		Page<Patient> patientsPage = patientRepository.findAll(PageRequest.of(FIRST_PAGE, BATCH_SIZE));
		addPatientDocuments(patientsPage.getContent());
		
		while (patientsPage.hasNext()) {
			patientsPage = patientRepository.findAll(patientsPage.nextPageable());
			addPatientDocuments(patientsPage.getContent());
		}
	}

	private void addPatientDocuments(List<Patient> patients) {
		final List<Document> patientDocuments = patients.stream().map(this::createPatientDocumentDto)
				.map(patientDocument -> new Document(patientDocument.toString())).toList();

//		vectorStore.add(patientDocuments);
	}

	private PatientDocumentDto createPatientDocumentDto(Patient patient) {
		return new PatientDocumentDto(patient.getId(), patient.getIllnessDescription(), patient.getRoom().getDepartment().getName(),
				patient.getDoctor().getFirstName(), patient.getDoctor().getLastName());
	}

}
