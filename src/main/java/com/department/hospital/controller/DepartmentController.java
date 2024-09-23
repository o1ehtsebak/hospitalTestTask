package com.department.hospital.controller;

import com.department.hospital.dto.DepartmentDto;
import com.department.hospital.dto.request.CreateUpdateDepartmentDto;
import com.department.hospital.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@GetMapping
	public ResponseEntity<DepartmentDto> getDepartment(@RequestParam String name) {
		return departmentService.findDepartmentByName(name)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
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
