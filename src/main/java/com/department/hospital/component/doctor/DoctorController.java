package com.department.hospital.component.doctor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

	private final DoctorService doctorService;

	@GetMapping
	public ResponseEntity<DoctorDto> getDoctor(@Valid @Min(1) @RequestParam Long id) {
		return doctorService.findDoctor(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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


