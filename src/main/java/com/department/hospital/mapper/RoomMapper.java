package com.department.hospital.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.department.hospital.dto.RoomDto;
import com.department.hospital.dto.request.CreateUpdateRoomDto;
import com.department.hospital.entity.Room;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

	RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

	RoomDto roomToRoomDto(Room room);

	Room updateRoomDtoToRoom(CreateUpdateRoomDto createUpdateRoomDto);

}
