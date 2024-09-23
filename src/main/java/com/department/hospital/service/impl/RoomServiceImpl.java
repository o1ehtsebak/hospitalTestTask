package com.department.hospital.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.department.hospital.dto.RoomDto;
import com.department.hospital.dto.request.CreateUpdateRoomDto;
import com.department.hospital.entity.Department;
import com.department.hospital.entity.Room;
import com.department.hospital.mapper.RoomMapper;
import com.department.hospital.repository.DepartmentsRepository;
import com.department.hospital.repository.RoomsRepository;
import com.department.hospital.service.RoomService;

import jakarta.persistence.EntityNotFoundException;


@Service
public class RoomServiceImpl implements RoomService {


	@Autowired
	private RoomsRepository roomsRepository;
	@Autowired
	private DepartmentsRepository departmentsRepository;
	@Autowired
	private RoomMapper roomMapper;

	@Override
	public Optional<RoomDto> findRoom(Long departmentId, Integer roomNumber) {
		return roomsRepository.getRoomByDepartmentIdAndNumber(departmentId, roomNumber).map(roomMapper::roomToRoomDto);
	}

	@Override
	public RoomDto createRoom(CreateUpdateRoomDto createUpdateRoomDto) {
		final Department department = departmentsRepository.findById(createUpdateRoomDto.getDepartmentId())
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("Department not found for ID - %s", createUpdateRoomDto.getDepartmentId())));
		final Room newRoom = roomMapper.updateRoomDtoToRoom(createUpdateRoomDto);
		newRoom.setDepartment(department);
		roomsRepository.save(newRoom);

		return roomMapper.roomToRoomDto(newRoom);
	}

	@Override
	public Optional<RoomDto> updateRoom(CreateUpdateRoomDto createUpdateRoomDto) {
//		return roomsRepository
//				.getRoomByDepartmentIdAndNumber(createUpdateRoomDto.getDepartmentId(), createUpdateRoomDto.getOriginalNumber())
//				.map(room -> updateRoom(createUpdateRoomDto, room));
		return null;

	}

	private RoomDto updateRoom(CreateUpdateRoomDto createUpdateRoomDto, Room room) {
//		room.setNumber(createUpdateRoomDto.getNumber());
//		room.setNumberOfPlaces(createUpdateRoomDto.getNumberOfPlaces());
//		roomsRepository.save(room);
//		return roomMapper.roomToRoomDto(room);
		return null;
	}
}
