package com.department.hospital.component.department;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService departmentService;

	@GetMapping
	public ResponseEntity<DepartmentDto> getDepartment(@RequestParam String name) {
		return departmentService.findDepartmentByName(name)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/load")
	public DepartmentLoadDto getDepartmentLoadInfo(@RequestParam Long id) {
		return departmentService.getDepartmentLoadInfo(id);
	}

	@PostMapping
	public ResponseEntity<CreateUpdateDepartmentResponseDto> createDepartment(@RequestBody @Valid CreateDepartmentRequestDto createDepartmentRequestDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(createDepartmentRequestDto));
	}

	@PutMapping("/{departmentId}")
	public ResponseEntity<CreateUpdateDepartmentResponseDto> updateDepartment(@PathVariable Long departmentId, @RequestBody @Valid UpdateDepartmentRequestDto updateDepartmentRequestDto) {
		updateDepartmentRequestDto.setDepartmentId(departmentId);
		return departmentService.updateDepartment(updateDepartmentRequestDto)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
