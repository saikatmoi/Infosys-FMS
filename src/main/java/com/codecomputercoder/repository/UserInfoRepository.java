package com.codecomputercoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecomputercoder.entity.UserInfo;

import java.math.BigInteger;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByUserName(String username);

    boolean existsByUserPhone(BigInteger userPhone);

    boolean existsByEmail(String email);

    boolean existsById(String userName);

    boolean existsByUserName(String userName);


}
