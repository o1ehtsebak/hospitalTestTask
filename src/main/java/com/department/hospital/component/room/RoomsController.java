package com.department.hospital.component.room;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.department.hospital.component.department.DepartmentsRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/departments")
public class RoomsController {

	private final RoomService roomService;
	private final RoomsRepository roomsRepository;
	private final DepartmentsRepository departmentsRepository;

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
