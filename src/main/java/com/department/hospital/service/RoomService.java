package com.department.hospital.service;

import java.util.Optional;

import com.department.hospital.dto.RoomDto;
import com.department.hospital.dto.request.CreateUpdateRoomDto;


public interface RoomService {

	Optional<RoomDto> findRoom(Long departmentId, Integer roomNumber);

	RoomDto createRoom(CreateUpdateRoomDto createUpdateRoomDto);

	Optional<RoomDto> updateRoom(CreateUpdateRoomDto createUpdateRoomDto);
}
