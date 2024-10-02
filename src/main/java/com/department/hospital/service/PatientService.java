package com.department.hospital.service;

import org.springframework.stereotype.Service;

import com.department.hospital.dto.PatientDto;
import com.department.hospital.dto.request.RegisterPatientDto;
import com.department.hospital.entity.*;
import com.department.hospital.mapper.PatientMapper;
import com.department.hospital.repository.*;
import com.department.hospital.service.mail.HospitalMailService;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class PatientService {

	private final PatientMapper patientMapper;
	private final HospitalMailService hospitalMailService;
	private final PatientRepository patientRepository;
	private final RoomsRepository roomsRepository;
	private final DoctorsRepository doctorsRepository;

	public PatientDto registerPatient(RegisterPatientDto registerPatientDto) {
		final Doctor doctor = doctorsRepository.findById(registerPatientDto.getDoctorId()).orElseThrow(EntityExistsException::new);
		final Room room = roomsRepository.findById(registerPatientDto.getRoomId()).orElseThrow(EntityExistsException::new);

		final Patient registeredPatient = patientMapper.patientDtoToPatient(registerPatientDto);
		registeredPatient.setDoctor(doctor);
		registeredPatient.setRoom(room);

		patientRepository.save(registeredPatient);
		hospitalMailService.sendNewPatientMsg(doctor.getEmail(), registeredPatient.getId(), registeredPatient.getFirstName(),
				registeredPatient.getLastName(), room.getNumber());
		return patientMapper.patientToPatientDto(registeredPatient);
	}
}
