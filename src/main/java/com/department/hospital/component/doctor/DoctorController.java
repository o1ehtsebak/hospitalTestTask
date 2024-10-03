package com.department.hospital.component.doctor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.department.hospital.component.department.DepartmentRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/doctors")
public class DoctorController {

	private final DoctorService doctorService;
	private final DepartmentRepository departmentRepository;

	@GetMapping
	public ResponseEntity<DoctorDto> getDoctor(@Valid @Min(1) @RequestParam Long doctorId) {
		return doctorService.findDoctor(doctorId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<DoctorResponseDto> createDoctor(@RequestBody @Valid DoctorRequestDto doctorRequestDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(doctorRequestDto));
	}

	@PutMapping("/{doctorId}")
	public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable Long doctorId, @RequestBody @Valid DoctorRequestDto doctorRequestDto) {
		return doctorService.updateDoctor(doctorId, doctorRequestDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
