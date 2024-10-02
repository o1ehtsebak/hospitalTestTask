package com.department.hospital.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.department.hospital.dto.PatientDto;
import com.department.hospital.dto.request.RegisterPatientDto;
import com.department.hospital.service.PatientService;

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
