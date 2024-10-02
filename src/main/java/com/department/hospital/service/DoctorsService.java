package com.department.hospital.service;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.department.hospital.dto.DoctorDto;
import com.department.hospital.dto.request.CreateUpdateDoctorDto;
import com.department.hospital.entity.Department;
import com.department.hospital.entity.Doctor;
import com.department.hospital.mapper.DoctorMapper;
import com.department.hospital.repository.DepartmentsRepository;
import com.department.hospital.repository.DoctorsRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class DoctorsService {

	private final DoctorMapper doctorMapper;
	private final DoctorsRepository doctorsRepository;
	private final DepartmentsRepository departmentsRepository;

	public Optional<DoctorDto> findDoctor(Long id) {
		return doctorsRepository.findById(id).map(doctorMapper::doctorToDoctorDto);
	}

	public DoctorDto createDoctor(CreateUpdateDoctorDto createUpdateDoctorDto) {
		final List<Department> departments = departmentsRepository.findAllById(createUpdateDoctorDto.getDepartments());

		final Doctor newDoctor = doctorMapper.updateDoctorDtoToDoctor(createUpdateDoctorDto);
		newDoctor.setDepartments(departments);
		departments.forEach(dep -> dep.getDoctors().add(newDoctor));

		doctorsRepository.save(newDoctor);
		return doctorMapper.doctorToDoctorDto(newDoctor);
	}

	public Optional<DoctorDto> updateDoctor(CreateUpdateDoctorDto createUpdateDoctorDto) {
		final Optional<Doctor> doctorOptional = doctorsRepository.findById(createUpdateDoctorDto.getId());
		if (doctorOptional.isPresent()) {
			final Doctor doctor = doctorOptional.get();
			final List<Department> oldDepartments = doctor.getDepartments();

			final List<Department> newDepartments = departmentsRepository.findAllById(createUpdateDoctorDto.getDepartments());
			doctor.setFirstName(createUpdateDoctorDto.getFirstName());
			doctor.setLastName(createUpdateDoctorDto.getLastName());
			doctor.setDepartments(newDepartments);
			newDepartments.stream().filter(dep -> isNotEmpty(dep.getDoctors())).filter(dep -> !dep.getDoctors().contains(doctor))
					.forEach(dep -> dep.getDoctors().add(doctor));

			oldDepartments.removeAll(newDepartments);
			oldDepartments.forEach(dep -> dep.getDoctors().remove(doctor));

			departmentsRepository.saveAll(oldDepartments);
			doctorsRepository.save(doctor);
		}

		return doctorOptional.map(doctorMapper::doctorToDoctorDto);
	}
}
