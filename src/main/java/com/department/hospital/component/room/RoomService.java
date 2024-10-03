package com.department.hospital.component.room;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.department.hospital.component.department.Department;
import com.department.hospital.component.department.DepartmentRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class RoomService {


	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private RoomMapper roomMapper;

	public Optional<RoomDto> findRoom(Long departmentId, Integer roomNumber) {
		return roomRepository.getRoomByDepartmentIdAndNumber(departmentId, roomNumber).map(roomMapper::roomToRoomDto);
	}

	public CreateUpdateRoomResponseDto createRoom(CreateUpdateRoomRequestDto createUpdateRoomRequestDto) {
		final Department department = departmentRepository.findById(createUpdateRoomRequestDto.getDepartmentId())
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("Department not found for ID - %s", createUpdateRoomRequestDto.getDepartmentId())));
		final Room newRoom = roomMapper.updateRoomDtoToRoom(createUpdateRoomRequestDto);
		newRoom.setDepartment(department);
		roomRepository.save(newRoom);

		return new CreateUpdateRoomResponseDto(roomMapper.roomToRoomDto(newRoom));
	}

	public Optional<CreateUpdateRoomResponseDto> updateRoom(CreateUpdateRoomRequestDto createUpdateRoomRequestDto) {
		return roomRepository
				.getRoomByDepartmentIdAndNumber(createUpdateRoomRequestDto.getDepartmentId(),
						createUpdateRoomRequestDto.getOriginalNumber())
				.map(room -> updateRoom(createUpdateRoomRequestDto, room));
	}

	private CreateUpdateRoomResponseDto updateRoom(CreateUpdateRoomRequestDto createUpdateRoomRequestDto, Room room) {
		room.setNumber(createUpdateRoomRequestDto.getNumber());
		room.setNumberOfPlaces(createUpdateRoomRequestDto.getNumberOfPlaces());
		roomRepository.save(room);
		return new CreateUpdateRoomResponseDto(roomMapper.roomToRoomDto(room));
	}
}
