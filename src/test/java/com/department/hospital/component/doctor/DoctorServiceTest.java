package com.department.hospital.component.doctor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.department.hospital.component.department.Department;
import com.department.hospital.component.department.DepartmentRepository;


@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

	private static final long DOCTOR_ID = 1L;
	private static final long FIRST_DEPARTMENT_ID = 1L;
	private static final long SECOND_DEPARTMENT_ID = 2L;
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	@Mock
	DoctorMapper doctorMapper;
	@Mock
	DoctorRepository doctorRepository;
	@Mock
	DepartmentRepository departmentRepository;

	@InjectMocks
	DoctorService testedInstance;

	@Test
	void shouldFindDoctorById() {
		//given
		final Doctor doctor = new Doctor();
		when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(doctor));
		final DoctorDto expectedDoctorDto = mock(DoctorDto.class);
		when(doctorMapper.doctorToDoctorDto(doctor)).thenReturn(expectedDoctorDto);

		//when
		final Optional<DoctorDto> doctorDto = testedInstance.findDoctor(DOCTOR_ID);

		//then
		assertTrue(doctorDto.isPresent());
		assertEquals(expectedDoctorDto, doctorDto.get());
	}

	@Test
	void shouldCreateDoctor() {
		//given
		final List<Long> departmentIds = List.of(FIRST_DEPARTMENT_ID, SECOND_DEPARTMENT_ID);
		final Department firstDepartment = new Department();
		firstDepartment.setDoctors(new ArrayList<>());
		final Department secondDepartment = new Department();
		secondDepartment.setDoctors(new ArrayList<>());
		final List<Department> doctorDepartments = List.of(firstDepartment, secondDepartment);
		when(departmentRepository.findAllById(departmentIds)).thenReturn(doctorDepartments);

		final DoctorRequestDto createDoctorDto = new DoctorRequestDto(FIRST_NAME, LAST_NAME, departmentIds);
		final Doctor newDoctor = new Doctor();
		when(doctorMapper.doctorRequestDtoToDoctor(createDoctorDto)).thenReturn(newDoctor);
		final DoctorDto expectedDoctorDto = mock(DoctorDto.class);
		when(doctorMapper.doctorToDoctorDto(newDoctor)).thenReturn(expectedDoctorDto);

		//when
		final DoctorResponseDto doctorResponse = testedInstance.createDoctor(createDoctorDto);

		//then
		assertEquals(doctorDepartments, newDoctor.getDepartments());
		assertTrue(firstDepartment.getDoctors().contains(newDoctor));
		assertTrue(secondDepartment.getDoctors().contains(newDoctor));
		assertEquals(expectedDoctorDto, doctorResponse.doctor());
	}

	@Test
	void shouldUpdateDoctor() {
		//given
		final List<Long> departmentIds = Arrays.asList(FIRST_DEPARTMENT_ID, SECOND_DEPARTMENT_ID);
		final Department firstDepartment = new Department();
		firstDepartment.setId(FIRST_DEPARTMENT_ID);
		firstDepartment.setDoctors(new ArrayList<>());
		final Department secondDepartment = new Department();
		secondDepartment.setId(SECOND_DEPARTMENT_ID);
		secondDepartment.setDoctors(new ArrayList<>());
		final List<Department> doctorDepartments = Arrays.asList(firstDepartment, secondDepartment);
		when(departmentRepository.findAllById(departmentIds)).thenReturn(doctorDepartments);

		final Doctor existingDoctor = new Doctor();
		final Department oldDoctorDepartment = new Department();
		final List<Doctor> doctors = new ArrayList<>();
		doctors.add(existingDoctor);
		oldDoctorDepartment.setDoctors(doctors);
		final ArrayList<Department> existingDepartments = new ArrayList<>();
		existingDepartments.add(oldDoctorDepartment);
		existingDoctor.setDepartments(existingDepartments);

		when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(existingDoctor));
		final DoctorRequestDto updateDoctorDto = new DoctorRequestDto(FIRST_NAME, LAST_NAME, departmentIds);
		final DoctorDto expectedDoctorDto = mock(DoctorDto.class);
		when(doctorMapper.doctorToDoctorDto(existingDoctor)).thenReturn(expectedDoctorDto);

		//when
		final Optional<DoctorResponseDto> doctorResponseDto = testedInstance.updateDoctor(DOCTOR_ID, updateDoctorDto);

		//then
		assertTrue(doctorResponseDto.isPresent());
		assertEquals(expectedDoctorDto, doctorResponseDto.get().doctor());
		assertEquals(FIRST_NAME, existingDoctor.getFirstName());
		assertEquals(LAST_NAME, existingDoctor.getLastName());
		assertEquals(doctorDepartments, existingDoctor.getDepartments());
		assertTrue(oldDoctorDepartment.getDoctors().isEmpty());
		assertTrue(firstDepartment.getDoctors().contains(existingDoctor));
		assertTrue(secondDepartment.getDoctors().contains(existingDoctor));
	}
}