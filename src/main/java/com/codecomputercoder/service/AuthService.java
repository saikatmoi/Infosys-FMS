package com.codecomputercoder.service;


import java.util.Optional;

import com.codecomputercoder.email.EmailServiceImpl;
import com.codecomputercoder.entity.*;
import com.codecomputercoder.repository.ConfirmationTokenRepository;
import com.codecomputercoder.repository.TempUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codecomputercoder.repository.UserInfoRepository;


@Service
public class AuthService {

  

    @Autowired
    private UserInfoRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	private TempUserRepository tempUserRepository;

	@Autowired
	private EmailServiceImpl emailService;

    public ResponseEntity<?> addUser(TempUser userInfo) {
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
        tempUserRepository.save(userInfo);
		ConfirmationToken confirmationToken = new ConfirmationToken(userInfo);

		confirmationTokenRepository.save(confirmationToken);
		EmailDetails emailDetails=new EmailDetails();
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		emailDetails.setRecipient(userInfo.getEmail());
		emailDetails.setSubject("Complete Infosys-FMS Registration!");
		emailDetails.setMsgBody("To confirm your account, please click here : "
				+"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());
		emailService.sendSimpleMail(emailDetails);

		System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());
		return new ResponseEntity<>("Activate your account by the link sent to your email address",HttpStatus.OK);
    }


	public ResponseEntity<?> confirmEmail(String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if(token != null)
		{
			TempUser tempUser = tempUserRepository.findByEmailIgnoreCase(token.getUser().getEmail());
			UserInfo userInfo = new UserInfo();
			userInfo.setEmail(tempUser.getEmail());
			userInfo.setUserName(tempUser.getUserName());
			userInfo.setPassword(tempUser.getPassword());
			userInfo.setUserPhone(tempUser.getUserPhone());
			userInfo.setRole(tempUser.getRole());
			userRepo.save(userInfo);
			return ResponseEntity.ok("Email verified successfully!");
		}
		return ResponseEntity.badRequest().body("Error: Couldn't verify email");
	}

	public String getRole(String UserName){
		Optional<UserInfo> user =userRepo.findByUserName(UserName);
		return user.get().getRole();
	}


}
