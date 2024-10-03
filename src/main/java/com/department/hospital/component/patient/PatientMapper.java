package com.department.hospital.component.patient;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {

	PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

	@Mapping(source = "doctor.id", target = "doctorId")
	PatientDto patientToPatientDto(Patient patient);

	@Mapping(target = "id", ignore = true)
	Patient patientDtoToPatient(RegisterPatientDto registerPatientDto);

}
