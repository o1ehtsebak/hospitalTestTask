package com.department.hospital.component.department;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;


public record CreateDepartmentRequestDto(@Valid @NotEmpty String name) {}
