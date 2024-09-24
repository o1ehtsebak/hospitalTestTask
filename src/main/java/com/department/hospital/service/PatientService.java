package com.department.hospital.service;

import com.department.hospital.dto.PatientDto;
import com.department.hospital.dto.request.RegisterPatientDto;

public interface PatientService {
	PatientDto registerPatient(RegisterPatientDto registerPatientDto);
}
