package com.example.springsecurity.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.model.Blog;
import com.example.springsecurity.model.Users;
import com.example.springsecurity.repo.BlogRepository;
import com.example.springsecurity.repo.UserRepo;
import com.example.springsecurity.service.BlogService;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired private BlogService blogService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Blog createBlog(@RequestBody Blog blog, Principal principal) {
        return blogService.createBlog(blog, principal.getName());
    }

    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/myblogs")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Blog> getMyBlogs(Principal principal) {
        return blogService.getBlogsByUsername(principal.getName());
    }

    @GetMapping("/userblogs/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Blog> getUserBlogs(@PathVariable String username) {
        return blogService.getBlogsByUsername(username);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateBlog(@PathVariable Long id, @RequestBody Blog updatedBlog, Principal principal) {
        return blogService.updateBlog(id, updatedBlog, principal.getName())
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You can only edit your own blog"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id, Principal principal) {
        return blogService.deleteBlog(id, principal.getName())
                ? ResponseEntity.ok("Deleted")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You can only delete your own blog");
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminDeleteBlog(@PathVariable Long id) {
        blogService.adminDeleteBlog(id);
        return ResponseEntity.ok("Admin deleted blog");
    }
}
