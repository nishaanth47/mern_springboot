package com.example.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecurity.dto.LoginDTO;
import com.example.springsecurity.dto.RegisterDTO;
import com.example.springsecurity.dto.UserResponseDTO;
import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.Users;
import com.example.springsecurity.repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // public Users register(Users user) {
    //     if (!user.getEmail().endsWith("@gmail.com")) {
    //         throw new IllegalArgumentException("Email must be a @gmail.com address");
    //     }
    //     user.setPassword(encoder.encode(user.getPassword()));
    //     if (user.getRole() == null) user.setRole(Role.USER); // default
    //     return repo.save(user);
    // }

    public UserResponseDTO register(RegisterDTO dto) {
        if (!dto.getEmail().endsWith("@gmail.com")) {
            throw new IllegalArgumentException("Email must be a @gmail.com address");
        }

        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() != null ? dto.getRole() : Role.USER);

        Users savedUser = repo.save(user);

        return new UserResponseDTO(
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail(),
            savedUser.getRole()
        );
    }

    // public String verify(Users user) {
    //     Authentication authentication = authManager.authenticate(
    //         new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
    //     );
    //     if (authentication.isAuthenticated())
    //         return jwtService.generateToken(user.getUsername());
    //     return "fail";
    // }

    public String verify(LoginDTO dto) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(dto.getUsername());
        }
        return "fail";
    }
}
