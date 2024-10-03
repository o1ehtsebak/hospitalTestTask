package com.department.hospital.component.room;

public record RoomLoadDto(long id, int number, int numberOfAvailablePlaces, String loadedPlacesPercentage) {
}
