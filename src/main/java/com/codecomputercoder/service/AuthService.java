package com.codecomputercoder.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codecomputercoder.entity.Message;
import com.codecomputercoder.entity.UserInfo;
import com.codecomputercoder.repository.UserInfoRepository;


@Service
public class AuthService {

  

    @Autowired
    private UserInfoRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public ResponseEntity<Message> addUser(UserInfo userInfo) {
        Message message=new Message();

        if (userRepo.existsByEmail(userInfo.getEmail())) {
			message.setInfo("Email already exists.");
			 return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		}
	
		if (userRepo.existsByUserName(userInfo.getUserName())) {
			message.setInfo("Username already exists.");
			 return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		}

		if (userRepo.existsByUserPhone(userInfo.getUserPhone())) {
			message.setInfo("Phone already exists.");
			 return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		}
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepo.save(userInfo);
        message.setInfo("Successfully Registered. Please Login.");
				return new ResponseEntity<>(message,HttpStatus.OK);
    }

	public String getRole(String UserName){
		Optional<UserInfo> user =userRepo.findByUserName(UserName);
		return user.get().getRole();
	}


}
