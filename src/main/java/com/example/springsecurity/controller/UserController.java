package com.example.springsecurity.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.dto.LoginDTO;
import com.example.springsecurity.dto.RegisterDTO;
import com.example.springsecurity.dto.UserResponseDTO;
import com.example.springsecurity.model.Users;
import com.example.springsecurity.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    @Autowired
    private UserService service;

    // @PostMapping("/register")
    // public Users register(@RequestBody Users user) {
    //     return service.register(user);
    // }

    // @PostMapping("/login")
    // public String login(@RequestBody Users user) {
    //     return service.verify(user);
    // }

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody RegisterDTO registerDto) {
        return service.register(registerDto);
    }

//    @PostMapping("/login")
//    public String login(@RequestBody LoginDTO loginDto) {
//        return service.verify(loginDto);
//    }
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginDTO loginDto) {
        String token = service.verify(loginDto);
        return Collections.singletonMap("token", token);
    }

}
