package com.department.hospital.component.patient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientsController {

	private final PatientService patientService;

	@PostMapping
	public ResponseEntity<PatientDto> registerPatient(@RequestBody @Valid RegisterPatientDto registerPatientDto) {
		PatientDto registeredPatient = patientService.registerPatient(registerPatientDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(registeredPatient);
	}

}
