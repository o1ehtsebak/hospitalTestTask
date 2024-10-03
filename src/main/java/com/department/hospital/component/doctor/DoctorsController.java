package com.department.hospital.component.doctor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.department.hospital.component.department.DepartmentsRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/doctors")
public class DoctorsController {

	private final DoctorsService doctorsService;
	private final DepartmentsRepository departmentsRepository;

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
