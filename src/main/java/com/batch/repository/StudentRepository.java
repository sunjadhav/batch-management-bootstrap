package com.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.batch.model.Batch;
import com.batch.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

	@Query("SELECT a FROM Student a where a.batch= (?1) ")
    List<Student> findByBatch(Batch batch);
	
	@Query("SELECT a FROM Student a where a.id= (?1) and a.batch= (?2) ")
	Student findByIdAndBatch(String studentId, Batch batch);
	
	@Query("SELECT a FROM Student a where a.name= (?1) ")
	List<Student> findByName(String name);
	
	@Query("SELECT a FROM Student a where a.surname= (?1) ")
	List<Student> findBySurname(String surname);
	
	@Query("SELECT a FROM Student a where a.name= (?1) or a.surname= (?2) ")
	List<Student> findByNameOrSurname(String name, String surname);
	
	@Query("SELECT a FROM Student a where a.name= (?1) and a.surname= (?2) ")
	List<Student> findByNameAndSurname(String name, String surname);
	
	@Query("SELECT a FROM Student a where a.name= (?1) and a.batch= (?2) ")
	List<Student> findByNameAndBatch(String name, Batch batch);
	
	@Query("SELECT a FROM Student a where a.surname= (?1) and a.batch= (?2) ")
	List<Student> findBySurnameAndBatch(String surname, Batch batch);
	
	@Query("SELECT a FROM Student a where a.name= (?1) or a.surname= (?2) and a.batch= (?3) ")
	List<Student> findByNameOrSurnameAndBatch(String name, String surname, Batch batch);
	
	@Query("SELECT a FROM Student a where a.name= (?1) and a.surname= (?2) and a.batch= (?3) ")
	List<Student> findByNameAndSurnameAndBatch(String name, String surname, Batch batch);
	
	@Query("SELECT a FROM Student a where a.name= (?1) and a.surname= (?2) and a.phoneNumber= (?3) ")
	Student findByNameAndSurnameAndPhonenumber(String name, String surname, String phoneNumber);
}
