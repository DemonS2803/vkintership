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

import com.example.internship.vkintership.entities.cached.User;
import com.example.internship.vkintership.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    
    @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User userData = userService.getUserById(id);
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            var usersData = userService.getAllUsers();
            return new ResponseEntity<>(usersData, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editUserById(@PathVariable("id") Long userId, @RequestBody User user) {
        try {
            user = userService.updateUser(userId, user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error while updating User");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            user = userService.createUser( user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("error while creating User");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/edit/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("error while creating User");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
