package com.codecomputercoder.repository;

import com.codecomputercoder.entity.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempUserRepository extends JpaRepository<TempUser, Integer> {
    TempUser findByEmailIgnoreCase(String emailId);
}
