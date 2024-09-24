package com.department.hospital.mapper;

import com.department.hospital.dto.DoctorDto;
import com.department.hospital.dto.request.CreateUpdateDoctorDto;
import com.department.hospital.entity.Doctor;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PatientMapper.class})
public interface DoctorMapper {

	DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

	DoctorDto doctorToDoctorDto(Doctor doctor);

	@Mapping(target = "departments", ignore = true)
	Doctor updateDoctorDtoToDoctor(CreateUpdateDoctorDto createUpdateDoctorDto);

}
