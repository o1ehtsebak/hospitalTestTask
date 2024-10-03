package com.department.hospital.component.patient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientController {

	private final PatientService patientService;

	@PostMapping
	public ResponseEntity<RegisterPatientResponseDto> registerPatient(@RequestBody @Valid RegisterPatientRequestDto registerPatientRequestDto) {
		RegisterPatientResponseDto registeredPatient = patientService.registerPatient(registerPatientRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(registeredPatient);
	}
}
