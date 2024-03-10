package com.example.internship.vkintership.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.internship.vkintership.entities.User;
import com.example.internship.vkintership.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }
    
}
