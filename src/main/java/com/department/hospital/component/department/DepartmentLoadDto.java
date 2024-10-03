package com.department.hospital.component.department;

import com.department.hospital.component.room.RoomLoadDto;

import java.util.List;


public record DepartmentLoadDto(long id, String name, List<RoomLoadDto> rooms) {
}
