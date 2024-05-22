package com.codecomputercoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.codecomputercoder.dto.AuthRequest;
import com.codecomputercoder.entity.Message;
import com.codecomputercoder.entity.UserInfo;
import com.codecomputercoder.model.LoginSuccess;
import com.codecomputercoder.service.AuthService;
import com.codecomputercoder.service.JwtService;



@RestController
public class AuthController {

    @Autowired
    private AuthService service;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/registration")
    public ResponseEntity<?> addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    @GetMapping("/welcomeAdmin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Message> loggedInAdmin() {
        Message message=new Message();
		message.setInfo("Welcome Admin"); 

		return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @GetMapping("/welcomeUser")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Message> loggedInUser() {
        Message message=new Message();
		message.setInfo("Welcome User"); 

		return new ResponseEntity<>(message,HttpStatus.OK);
    }


    @PostMapping("/genToken")
    public ResponseEntity<LoginSuccess> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        
        LoginSuccess loginSuccess = new LoginSuccess();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
                
		   loginSuccess.setToken(jwtService.generateToken(authRequest.getUserName()));
           loginSuccess.setRole(service.getRole(authRequest.getUserName()));
           loginSuccess.setUserName(authRequest.getUserName());
           return new ResponseEntity<>(loginSuccess,HttpStatus.OK) ;
                
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
       


    }
}
