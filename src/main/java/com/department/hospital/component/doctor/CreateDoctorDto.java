package com.department.hospital.component.doctor;


import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CreateDoctorDto(@NotBlank String firstName, @NotBlank String lastName, @NotNull List<Long> departments) {}
