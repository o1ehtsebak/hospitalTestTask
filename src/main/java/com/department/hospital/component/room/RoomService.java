package com.department.hospital.component.room;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.department.hospital.component.department.Department;
import com.department.hospital.component.department.DepartmentsRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;


@Service
public class RoomService {


	@Autowired
	private RoomsRepository roomsRepository;
	@Autowired
	private DepartmentsRepository departmentsRepository;
	@Autowired
	private RoomMapper roomMapper;

	public Optional<RoomDto> findRoom(Long departmentId, Integer roomNumber) {
		return roomsRepository.getRoomByDepartmentIdAndNumber(departmentId, roomNumber).map(roomMapper::roomToRoomDto);
	}

	public CreateUpdateRoomResponseDto createRoom(CreateUpdateRoomRequestDto createUpdateRoomRequestDto) {
		final Department department = departmentsRepository.findById(createUpdateRoomRequestDto.getDepartmentId())
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("Department not found for ID - %s", createUpdateRoomRequestDto.getDepartmentId())));
		final Room newRoom = roomMapper.updateRoomDtoToRoom(createUpdateRoomRequestDto);
		newRoom.setDepartment(department);
		roomsRepository.save(newRoom);

		return new CreateUpdateRoomResponseDto(roomMapper.roomToRoomDto(newRoom));
	}

	public Optional<CreateUpdateRoomResponseDto> updateRoom(CreateUpdateRoomRequestDto createUpdateRoomRequestDto) {
		return roomsRepository
				.getRoomByDepartmentIdAndNumber(createUpdateRoomRequestDto.getDepartmentId(),
						createUpdateRoomRequestDto.getOriginalNumber())
				.map(room -> updateRoom(createUpdateRoomRequestDto, room));
	}

	private CreateUpdateRoomResponseDto updateRoom(CreateUpdateRoomRequestDto createUpdateRoomRequestDto, Room room) {
		room.setNumber(createUpdateRoomRequestDto.getNumber());
		room.setNumberOfPlaces(createUpdateRoomRequestDto.getNumberOfPlaces());
		roomsRepository.save(room);
		return new CreateUpdateRoomResponseDto(roomMapper.roomToRoomDto(room));
	}
}
