package com.batch.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.batch.dto.StudentDTO;
import com.batch.model.Batch;
import com.batch.model.Student;
import com.batch.repository.BatchRepository;
import com.batch.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
public class StudentService {

	@Autowired
	private StudentRepository repo;
	@Autowired
	private BatchRepository batchRepository;
	@Autowired
	BatchService batchService;

	public List<Student> listAll() {
		return repo.findAll();
	}

	public Student get(Long id) {
		Optional<Student> student = repo.findById(id);
		if(student.isPresent())
			return student.get();
		else
			return null;
	}

	public void delete(long id) {
		repo.deleteById(id);
	}

	public Boolean saveStudent(StudentDTO studentDTO) {
		Student student = repo.findByNameAndSurnameAndPhonenumber(studentDTO.getName(), studentDTO.getSurname(),
				studentDTO.getPhoneNumber());
		if (student == null) {
			Batch batch = batchRepository.findByName(studentDTO.getBatchName());
			Student a = studentDtoToEntity(studentDTO, student);
			a.setBatch(batch);
			repo.save(a);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	public Boolean updateStudent(StudentDTO studentDTO) {
		try {
			Student student = get(studentDTO.getId());
			Batch batch = batchRepository.findByName(studentDTO.getBatchName());
			Student stud = studentDtoToEntity(studentDTO, student);
			stud.setBatch(batch);
			repo.save(stud);
			return Boolean.TRUE;
		} catch (Exception ex) {
			return Boolean.FALSE;
		}

	}

	public List<Student> search(String studentName, String batchName) {
		List<Student> students = new ArrayList<>();
		String[] names = studentName.split(" ");
		if (studentName.isEmpty() && batchName.isEmpty()) {
			return Collections.emptyList();
		}

		if (!batchName.isEmpty()) {
			Batch batch = batchRepository.findByName(batchName);
			if (names.length == 2 ) {
				students.addAll(repo.findByNameAndSurnameAndBatch(names[0], names[1], batch));
				students.addAll(repo.findByNameAndSurnameAndBatch(names[1], names[0], batch));
				return students;
			} else if (names.length == 1 && !studentName.isEmpty()) {
				 students.addAll(repo.findByNameAndBatch(names[0], batch));
				 students.addAll(repo.findBySurnameAndBatch(names[0], batch));
				return students;
			} else {
				return repo.findByBatch(batch);
			}
		} else if (names.length == 2) {
			students.addAll(repo.findByNameAndSurname(names[0], names[1]));
			students.addAll(repo.findByNameAndSurname(names[1], names[0]));
			return students;
		} else if (names.length == 1 && !studentName.isEmpty()){
			students.addAll(repo.findByName(names[0]));
			students.addAll(repo.findBySurname(names[0]));
			return students;
		}else {
			return Collections.emptyList();
		}

	}

	public Student studentDtoToEntity(StudentDTO studentDto, Student a) {

		Student student = (a == null ? new Student() : a);
		student.setName(studentDto.getName());
		student.setSurname(studentDto.getSurname());
		student.setAddress(studentDto.getAddress());
		student.setPhoneNumber(studentDto.getPhoneNumber());
		student.setCollege(studentDto.getCollege());
		student.setEducation(studentDto.getEducation());
		student.setPaidAmount(studentDto.getPaidAmount());
		student.setPendingAmount(studentDto.getPendingAmount());

		return student;

	}

	public StudentDTO studentEntityToDto(Student student) {
		StudentDTO studentDto = new StudentDTO();
		if (student != null) {
			studentDto.setId(student.getId());
			studentDto.setName(student.getName());
			studentDto.setSurname(student.getSurname());
			studentDto.setAddress(student.getAddress());
			studentDto.setPhoneNumber(student.getPhoneNumber());
			studentDto.setCollege(student.getCollege());
			studentDto.setEducation(student.getEducation());
			studentDto.setPaidAmount(student.getPaidAmount());
			studentDto.setPendingAmount(student.getPendingAmount());
			studentDto.setBatchName(student.getBatch().getName());
			studentDto.setBatchStatus(batchService.statusConverter(student.getBatch().getIsActive()));
		}
		return studentDto;

	}
}
