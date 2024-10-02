package com.department.hospital.dto;

public record RoomLoadDto(long id, int number, int numberOfAvailablePlaces, String loadedPlacesPercentage) {
}
