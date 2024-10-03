package com.department.hospital.component.patient;

import org.springframework.stereotype.Service;

import com.department.hospital.component.doctor.Doctor;
import com.department.hospital.component.doctor.DoctorRepository;
import com.department.hospital.component.doctor.mail.HospitalMailService;
import com.department.hospital.component.room.Room;
import com.department.hospital.component.room.RoomRepository;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class PatientService {

	private final PatientMapper patientMapper;
	private final HospitalMailService hospitalMailService;
	private final PatientRepository patientRepository;
	private final RoomRepository roomRepository;
	private final DoctorRepository doctorRepository;

	public RegisterPatientResponseDto registerPatient(RegisterPatientRequestDto registerPatientRequestDto) {
		final Doctor doctor = doctorRepository.findById(registerPatientRequestDto.getDoctorId())
				.orElseThrow(EntityExistsException::new);
		final Room room = roomRepository.findById(registerPatientRequestDto.getRoomId()).orElseThrow(EntityExistsException::new);

		final Patient registeredPatient = patientMapper.registerPatientRequestDtoToPatient(registerPatientRequestDto);
		registeredPatient.setDoctor(doctor);
		registeredPatient.setRoom(room);

		patientRepository.save(registeredPatient);
		hospitalMailService.sendNewPatientMsg(doctor.getEmail(), registeredPatient.getId(), registeredPatient.getFirstName(),
				registeredPatient.getLastName(), room.getNumber());
		final PatientDto patientDto = patientMapper.patientToRegisterPatientResponseDto(registeredPatient);
		return new RegisterPatientResponseDto(patientDto);
	}
}
