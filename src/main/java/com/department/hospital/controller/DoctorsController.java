package com.department.hospital.controller;

import com.department.hospital.dto.DoctorDto;
import com.department.hospital.dto.request.CreateUpdateDoctorDto;
import com.department.hospital.repository.DepartmentsRepository;
import com.department.hospital.service.DoctorsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorsController {

	@Autowired
	private DoctorsService doctorsService;
	@Autowired
	private DepartmentsRepository departmentsRepository;

	@GetMapping
	public ResponseEntity<DoctorDto> getDoctor(@Valid @Min(1) @RequestParam Long doctorId) {
		return doctorsService.findDoctor(doctorId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<DoctorDto> createDoctor(@RequestBody @Valid CreateUpdateDoctorDto createUpdateDoctorDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(doctorsService.createDoctor(createUpdateDoctorDto));
	}

	@PutMapping("/{doctorId}")
	public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long doctorId, @RequestBody @Valid CreateUpdateDoctorDto createUpdateDoctorDto) {
		createUpdateDoctorDto.setId(doctorId);
		return doctorsService.updateDoctor(createUpdateDoctorDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
