package com.example.springsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.model.Blog;
import com.example.springsecurity.model.Users;
import com.example.springsecurity.repo.BlogRepository;
import com.example.springsecurity.repo.UserRepo;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired private UserRepo userRepo;
    @Autowired private BlogRepository blogRepo;

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userRepo.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }

    @DeleteMapping("/user/{id}/blogs")
    public ResponseEntity<?> deleteUserBlogs(@PathVariable int id) {
        List<Blog> blogs = blogRepo.findByUserId(id);
        blogRepo.deleteAll(blogs);
        return ResponseEntity.ok("All blogs deleted for user id " + id);
    }
}

