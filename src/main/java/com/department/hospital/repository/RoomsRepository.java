package com.department.hospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.department.hospital.entity.Room;


@Repository
public interface RoomsRepository extends JpaRepository<Room, Long> {

	Optional<Room> getRoomByDepartmentIdAndNumber(final Long departmentId, final Integer number);
}
