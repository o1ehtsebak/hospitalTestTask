package com.department.hospital.dto;

import java.util.List;


public record DepartmentDto(long id, String name, List<RoomDto> rooms, List<DoctorDto> doctors) {
}

