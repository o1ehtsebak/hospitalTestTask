package com.department.hospital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.department.hospital.dto.PatientDto;
import com.department.hospital.dto.request.RegisterPatientDto;
import com.department.hospital.entity.*;
import com.department.hospital.mapper.PatientMapper;
import com.department.hospital.repository.*;
import com.department.hospital.service.PatientService;
import com.department.hospital.service.mail.HospitalMailService;

import jakarta.persistence.EntityExistsException;


@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private RoomsRepository roomsRepository;
	@Autowired
	private DoctorsRepository doctorsRepository;
	@Autowired
	private HospitalMailService hospitalMailService;
	@Autowired
	private PatientMapper patientMapper;

	@Override
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
