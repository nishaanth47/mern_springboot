package com.example.springsecurity.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.model.Users;
import com.example.springsecurity.repo.UserRepo;

@RestController
public class Hellocontroller {
	@GetMapping("/")
	public String greet()
	{
		return "welcome";
	}
	
}
