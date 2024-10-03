package com.department.hospital.component.doctor;

import com.department.hospital.component.patient.PatientMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PatientMapper.class})
public interface DoctorMapper {

	DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

	DoctorDto doctorToDoctorDto(Doctor doctor);

	@Mapping(target = "departments", ignore = true)
	Doctor updateDoctorDtoToDoctor(DoctorRequestDto doctorRequestDto);

}
