package com.jwt.implementation.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.implementation.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUserName(String username);

	boolean existsByEmail(String email);

	boolean existsByUserName(String userName);
	boolean existsByUserPhone(BigInteger userPhone);
}
