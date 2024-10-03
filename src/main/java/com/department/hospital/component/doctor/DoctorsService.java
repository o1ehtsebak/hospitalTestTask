package com.department.hospital.component.doctor;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.department.hospital.component.department.Department;
import com.department.hospital.component.department.DepartmentsRepository;

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

	public DoctorDto createDoctor(CreateDoctorDto createDoctorDto) {
		final List<Department> departments = departmentsRepository.findAllById(createDoctorDto.getDepartments());

		final Doctor newDoctor = doctorMapper.updateDoctorDtoToDoctor(createDoctorDto);
		newDoctor.setDepartments(departments);
		departments.forEach(dep -> dep.getDoctors().add(newDoctor));

		doctorsRepository.save(newDoctor);
		return doctorMapper.doctorToDoctorDto(newDoctor);
	}

	public Optional<DoctorDto> updateDoctor(CreateDoctorDto createDoctorDto) {
		final Optional<Doctor> doctorOptional = doctorsRepository.findById(createDoctorDto.getId());
		if (doctorOptional.isPresent()) {
			final Doctor doctor = doctorOptional.get();
			final List<Department> oldDepartments = doctor.getDepartments();

			final List<Department> newDepartments = departmentsRepository.findAllById(createDoctorDto.getDepartments());
			doctor.setFirstName(createDoctorDto.getFirstName());
			doctor.setLastName(createDoctorDto.getLastName());
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
