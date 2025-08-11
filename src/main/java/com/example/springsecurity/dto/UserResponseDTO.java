package com.example.springsecurity.dto;

 

import com.example.springsecurity.model.Role;

 

public class UserResponseDTO {
    private int id;
    private String username;
    private String email;
    private Role role;

 

    // Constructors
    public UserResponseDTO() {}

 

    public UserResponseDTO(int id, String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

 

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

 

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

 

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}