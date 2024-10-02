package com.department.hospital.dto;

import java.util.List;


public record DepartmentLoadDto(long id, String name, List<RoomLoadDto> rooms) {
}
