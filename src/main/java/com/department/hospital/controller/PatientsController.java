package com.department.hospital.controller;

import com.department.hospital.dto.PatientDto;
import com.department.hospital.dto.request.RegisterPatientDto;
import com.department.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.department.hospital.dto.RoomDto;
import com.department.hospital.dto.request.CreateUpdateRoomDto;
import com.department.hospital.repository.DepartmentsRepository;
import com.department.hospital.repository.RoomsRepository;
import com.department.hospital.service.RoomService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/patients")
public class PatientsController {

	@Autowired
	private PatientService patientService;

	@PostMapping
	public ResponseEntity<PatientDto> registerPatient(@RequestBody @Valid RegisterPatientDto registerPatientDto) {
		PatientDto registeredPatient = patientService.registerPatient(registerPatientDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(registeredPatient);
	}

}
