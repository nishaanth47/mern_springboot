package com.example.springsecurity.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springsecurity.exception.BlogNotFoundException;
import com.example.springsecurity.model.Blog;
import com.example.springsecurity.model.Users;
import com.example.springsecurity.repo.BlogRepository;
import com.example.springsecurity.repo.UserRepo;

@Service
public class BlogService {

    @Autowired 
    private BlogRepository blogRepository;

    @Autowired 
    private UserRepo userRepo;

    public Blog createBlog(Blog blog, String username) {
        Users user = userRepo.findByUsername(username);
        blog.setUser(user);
        blog.setCreatedAt(LocalDate.now());
        return blogRepository.save(blog);
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public List<Blog> getBlogsByUsername(String username) {
        Users user = userRepo.findByUsername(username);
        return blogRepository.findByUserId(user.getId());
    }

    public Optional<Blog> updateBlog(Long id, Blog updatedBlog, String username) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog with ID " + id + " not found"));

        if (!blog.getUser().getUsername().equals(username)) {
            return Optional.empty(); // handled in controller
        }

        blog.setTitle(updatedBlog.getTitle());
        blog.setDescription(updatedBlog.getDescription());
        blog.setImageUrl(updatedBlog.getImageUrl());
        return Optional.of(blogRepository.save(blog));
    }

    public boolean deleteBlog(Long id, String username) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog with ID " + id + " not found"));

        if (!blog.getUser().getUsername().equals(username)) {
            return false;
        }

        blogRepository.delete(blog);
        return true;
    }

    public void adminDeleteBlog(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new BlogNotFoundException("Blog with ID " + id + " not found");
        }
        blogRepository.deleteById(id);
    }
}
