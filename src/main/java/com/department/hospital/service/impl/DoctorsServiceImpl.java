package com.department.hospital.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.department.hospital.dto.DoctorDto;
import com.department.hospital.dto.request.CreateUpdateDoctorDto;
import com.department.hospital.entity.Department;
import com.department.hospital.entity.Doctor;
import com.department.hospital.mapper.DoctorMapper;
import com.department.hospital.repository.DepartmentsRepository;
import com.department.hospital.repository.DoctorsRepository;
import com.department.hospital.service.DoctorsService;


@Service
public class DoctorsServiceImpl implements DoctorsService {

	@Autowired
	private DoctorsRepository doctorsRepository;
	@Autowired
	private DepartmentsRepository departmentsRepository;
	@Autowired
	private DoctorMapper doctorMapper;

	@Override
	public Optional<DoctorDto> findDoctor(Long id) {
		return doctorsRepository.findById(id).map(doctorMapper::doctorToDoctorDto);
	}

	@Override
	public DoctorDto createDoctor(CreateUpdateDoctorDto createUpdateDoctorDto) {
		final List<Department> departments = departmentsRepository.findAllById(createUpdateDoctorDto.getDepartments());

		final Doctor newDoctor = doctorMapper.updateDoctorDtoToDoctor(createUpdateDoctorDto);
		newDoctor.setDepartments(departments);
		departments.forEach(dep -> dep.getDoctors().add(newDoctor));

		doctorsRepository.save(newDoctor);
		return doctorMapper.doctorToDoctorDto(newDoctor);
	}

	@Override
	public Optional<DoctorDto> updateDoctor(CreateUpdateDoctorDto createUpdateDoctorDto) {
		final Optional<Doctor> doctorOptional = doctorsRepository.findById(createUpdateDoctorDto.getId());
		if (doctorOptional.isPresent()) {
			final Doctor doctor = doctorOptional.get();
			final List<Department> oldDepartments = doctor.getDepartments();

			final List<Department> newDepartments = departmentsRepository.findAllById(createUpdateDoctorDto.getDepartments());
			doctor.setFirstName(createUpdateDoctorDto.getFirstName());
			doctor.setLastName(createUpdateDoctorDto.getLastName());
			doctor.setDepartments(newDepartments);
			newDepartments.forEach(dep -> dep.getDoctors().add(doctor));

			oldDepartments.removeAll(newDepartments);
			oldDepartments.forEach(dep -> dep.getDoctors().remove(doctor));

			departmentsRepository.saveAll(oldDepartments);
			departmentsRepository.saveAll(newDepartments);
		}

		return doctorOptional.map(doctorMapper::doctorToDoctorDto);
	}
}
