package com.department.hospital.component.room;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class RoomController {

	private final RoomService roomService;

	@GetMapping("/{departmentId}/rooms")
	public ResponseEntity<RoomDto> getRoom(@Valid @Min(1) @PathVariable Long departmentId, @Valid @Min(1) @RequestParam Integer roomNumber) {
		return roomService.findRoom(departmentId, roomNumber).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/{departmentId}/rooms")
	public ResponseEntity<CreateUpdateRoomResponseDto> createRoom(@Valid @Min(1) @PathVariable Long departmentId, @RequestBody @Valid CreateUpdateRoomRequestDto createUpdateRoomRequestDto) {
		createUpdateRoomRequestDto.setDepartmentId(departmentId);
		return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(createUpdateRoomRequestDto));
	}

	@PutMapping("/{departmentId}/rooms")
	public ResponseEntity<CreateUpdateRoomResponseDto> updateRoom(@PathVariable Long departmentId, @RequestBody @Valid CreateUpdateRoomRequestDto createUpdateRoomRequestDto) {
		createUpdateRoomRequestDto.setDepartmentId(departmentId);
		return roomService.updateRoom(createUpdateRoomRequestDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
