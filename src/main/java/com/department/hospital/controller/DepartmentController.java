package com.department.hospital.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.department.hospital.dto.DepartmentDto;
import com.department.hospital.dto.DepartmentLoadDto;
import com.department.hospital.dto.request.CreateUpdateDepartmentDto;
import com.department.hospital.service.DepartmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/departments")
public class DepartmentController {

	private final DepartmentService departmentService;

	@GetMapping
	public ResponseEntity<DepartmentDto> getDepartment(@RequestParam String name) {
		return departmentService.findDepartmentByName(name)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/stats")
	public DepartmentLoadDto getDepartmentLoadInfo(@RequestParam Long id) {
		return departmentService.getDepartmentLoadInfo(id);
	}

	@PostMapping
	public ResponseEntity<DepartmentDto> createDepartment(@RequestBody @Valid CreateUpdateDepartmentDto createUpdateDepartmentDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(createUpdateDepartmentDto));
	}

	@PutMapping("/{departmentId}")
	public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Long departmentId, @RequestBody @Valid CreateUpdateDepartmentDto createUpdateDepartmentDto) {
		createUpdateDepartmentDto.setDepartmentId(departmentId);
		return departmentService.updateDepartment(createUpdateDepartmentDto)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}
