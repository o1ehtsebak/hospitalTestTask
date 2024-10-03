package com.department.hospital.component.department;

import com.department.hospital.component.room.RoomDto;
import com.department.hospital.component.doctor.DoctorDto;

import java.util.List;


public record DepartmentDto(long id, String name, List<RoomDto> rooms, List<DoctorDto> doctors) {
}

