package com.department.hospital.mapper;

import com.department.hospital.dto.request.RegisterPatientDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.department.hospital.dto.PatientDto;
import com.department.hospital.entity.Patient;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {

	PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

	@Mapping(source = "doctor.id", target = "doctorId")
	PatientDto patientToPatientDto(Patient patient);

	@Mapping(target = "id", ignore = true)
	Patient patientDtoToPatient(RegisterPatientDto registerPatientDto);

}
