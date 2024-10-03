package com.department.hospital.component.department;

import com.department.hospital.component.room.RoomMapper;
import com.department.hospital.component.doctor.DoctorMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { DoctorMapper.class, RoomMapper.class })
public interface DepartmentMapper {

	DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

	DepartmentDto departmentToDepartmentDto(Department department);

	CreateUpdateDepartmentResponseDto departmentToDepartmentResponse(Department department);
}
