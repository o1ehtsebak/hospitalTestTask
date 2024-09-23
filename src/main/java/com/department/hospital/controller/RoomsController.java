package com.department.hospital.controller;

import com.department.hospital.dto.RoomDto;
import com.department.hospital.dto.request.CreateUpdateRoomDto;
import com.department.hospital.entity.Department;
import com.department.hospital.entity.Room;
import com.department.hospital.repository.DepartmentsRepository;
import com.department.hospital.repository.RoomsRepository;
import com.department.hospital.service.RoomService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class RoomsController {

	@Autowired
	private RoomService roomService;
	@Autowired
	private RoomsRepository roomsRepository;
	@Autowired
	private DepartmentsRepository departmentsRepository;

	@GetMapping("/{departmentId}/rooms")
	public ResponseEntity<RoomDto> getRoom(@Valid @Min(1) @PathVariable Long departmentId, @Valid @Min(1) @RequestParam Integer roomNumber) {
		return roomService.findRoom(departmentId, roomNumber).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/{departmentId}/rooms")
	public ResponseEntity<RoomDto> createRoom(@Valid @Min(1) @PathVariable Long departmentId, @RequestBody @Valid CreateUpdateRoomDto createUpdateRoomDto) {
		createUpdateRoomDto.setDepartmentId(departmentId);
		return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(createUpdateRoomDto));
	}

	@PutMapping("/{departmentId}/rooms")
	public ResponseEntity<RoomDto> updateRoom(@PathVariable Long departmentId, @RequestBody @Valid CreateUpdateRoomDto createUpdateRoomDto) {
		createUpdateRoomDto.setDepartmentId(departmentId);
		return roomService.updateRoom(createUpdateRoomDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
