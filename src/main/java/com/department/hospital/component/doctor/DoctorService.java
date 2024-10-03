package com.department.hospital.component.doctor;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.department.hospital.component.department.Department;
import com.department.hospital.component.department.DepartmentRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class DoctorService {

	private final DoctorMapper doctorMapper;
	private final DoctorRepository doctorRepository;
	private final DepartmentRepository departmentRepository;

	public Optional<DoctorDto> findDoctor(Long id) {
		return doctorRepository.findById(id).map(doctorMapper::doctorToDoctorDto);
	}

	public DoctorResponseDto createDoctor(DoctorRequestDto doctorRequestDto) {
		final List<Department> departments = departmentRepository.findAllById(doctorRequestDto.departments());

		final Doctor newDoctor = doctorMapper.updateDoctorDtoToDoctor(doctorRequestDto);
		newDoctor.setDepartments(departments);
		departments.forEach(dep -> dep.getDoctors().add(newDoctor));

		doctorRepository.save(newDoctor);
		return new DoctorResponseDto(doctorMapper.doctorToDoctorDto(newDoctor));
	}

	public Optional<DoctorResponseDto> updateDoctor(Long doctorId, DoctorRequestDto createDoctorDto) {
		return doctorRepository.findById(doctorId).map(doctor -> makeUpdate(doctor, createDoctorDto)).map(DoctorResponseDto::new);
	}

	private DoctorDto makeUpdate(Doctor doctor, DoctorRequestDto createDoctorDto) {
		final List<Department> oldDepartments = doctor.getDepartments();
		final List<Department> newDepartments = departmentRepository.findAllById(createDoctorDto.departments());
		doctor.setFirstName(createDoctorDto.firstName());
		doctor.setLastName(createDoctorDto.lastName());
		doctor.setDepartments(newDepartments);
		newDepartments.stream().filter(dep -> isNotEmpty(dep.getDoctors())).filter(dep -> !dep.getDoctors().contains(doctor))
				.forEach(dep -> dep.getDoctors().add(doctor));

		oldDepartments.removeAll(newDepartments);
		oldDepartments.forEach(dep -> dep.getDoctors().remove(doctor));

		departmentRepository.saveAll(oldDepartments);
		doctorRepository.save(doctor);

		return doctorMapper.doctorToDoctorDto(doctor);
	}
}
