package com.department.hospital.service;

import java.util.Optional;

import com.department.hospital.dto.DoctorDto;
import com.department.hospital.dto.request.CreateUpdateDoctorDto;


public interface DoctorsService {

	Optional<DoctorDto> findDoctor(Long id);

	DoctorDto createDoctor(CreateUpdateDoctorDto doctorDto);

	Optional<DoctorDto> updateDoctor(CreateUpdateDoctorDto doctorDto);
}
