package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.batch.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT a FROM User a where a.userName= (?1) and a.password = (?2) ")
    User findByUserNameAndPassword(String userName, String password);

}
