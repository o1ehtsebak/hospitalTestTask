package com.department.hospital.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.department.hospital.dto.DepartmentDto;
import com.department.hospital.entity.Department;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { DoctorMapper.class, RoomMapper.class })
public interface DepartmentMapper {

	DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

	DepartmentDto departmentToDepartmentDto(Department department);
}
