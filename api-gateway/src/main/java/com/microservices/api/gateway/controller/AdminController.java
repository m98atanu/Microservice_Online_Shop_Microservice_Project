package com.microservices.api.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.api.gateway.entity.Admin;
import com.microservices.api.gateway.repository.AdminRepository;

@RestController
public class AdminController {
	
	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired PasswordEncoder passwordEncoder;
	
	@PostMapping("/register/admin")
	public ResponseEntity<String> addAdmin(@RequestBody Admin admin){
		adminRepo.findByEmail(admin.getEmail()).ifPresent(amdin->{
			throw new RuntimeException("your record is already present as admin in database");
		});
		String hashPwd = passwordEncoder.encode(admin.getPwd());
		admin.setPwd(hashPwd);
		long newAdminId = adminRepo.save(admin).getId();
		return new ResponseEntity<String>("successfully added in db. your admin id is: " + newAdminId, HttpStatus.CREATED);
	}
	
	//Its a demo page. it is secured and nothing else
	@GetMapping("/demo/secured")
	public String demo() {
		return "you are viewing secured page";
	}

	//Here after register admin need to log in to get the jwt in client application's header
	//ONLY ADMIN IS ALLOWED TO ADD PRODUCT IN DATABASE
	@GetMapping("/login/admin")
	public String login() {
		return "you are successfully logged in";
	}
}
