package com.cds.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cds.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
    @Query("SELECT e from User e where e.salary BETWEEN ?1 and ?2 ")       // using @query
    List<User> findUsers(BigDecimal min, BigDecimal max);
}