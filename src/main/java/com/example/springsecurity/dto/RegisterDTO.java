package com.example.springsecurity.dto;

 

import com.example.springsecurity.model.Role;

 

public class RegisterDTO {
    private String username;
    private String email;
    private String password;
    private Role role; // optional

 

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

 

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

 

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

 

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}