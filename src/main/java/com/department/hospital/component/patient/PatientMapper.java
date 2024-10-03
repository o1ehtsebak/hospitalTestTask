package com.department.hospital.component.patient;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {

	PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

	@Mapping(target = "doctorId", source = "doctor.id")
	PatientDto patientToRegisterPatientResponseDto(Patient patient);

	@Mapping(target = "id", ignore = true)
	Patient registerPatientRequestDtoToPatient(RegisterPatientRequestDto registerPatientRequestDto);

}
