package com.example.internship.vkintership.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.internship.vkintership.entities.cached.Post;
import com.example.internship.vkintership.services.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        try {
            Post postData = postService.getPostById(id);
            return new ResponseEntity<>(postData, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllPosts() {
        try {
            var postsData = postService.getAllPosts();
            return new ResponseEntity<>(postsData, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editPostById(@PathVariable("id") Long postId, @RequestBody Post post) {
        try {
            post = postService.updatePost(postId, post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error while updating post");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit/create")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        try {
            post = postService.createPost( post);
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("error while creating post");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/edit/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long postId) {
        try {
            postService.deletePost(postId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("error while creating post");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
}
