package com.department.hospital.dto.request;


import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CreateUpdateRoomDto {

	private Long departmentId;
	@Min(1)
	private Integer originalNumber;
	@Min(1)
	private Integer number;
	@Min(1)
	private Integer numberOfPlaces;

}
