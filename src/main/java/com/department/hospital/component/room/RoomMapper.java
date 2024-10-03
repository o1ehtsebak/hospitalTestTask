package com.department.hospital.component.room;

import com.department.hospital.component.patient.PatientMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PatientMapper.class})
public interface RoomMapper {

	RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

	RoomDto roomToRoomDto(Room room);

	Room updateRoomDtoToRoom(CreateUpdateRoomRequestDto createUpdateRoomRequestDto);

}
