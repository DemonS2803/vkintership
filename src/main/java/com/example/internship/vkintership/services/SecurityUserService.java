package com.example.internship.vkintership.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.internship.vkintership.entities.User;
import com.example.internship.vkintership.repositories.UserRepository;

@Service
public class SecurityUserService {
    
    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = "security_users")
    public User getUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    public User createUser(User user) {
        var users = getAllUsers();
        Long lastId = 1L;
        for (User u: users) {
            lastId = Math.max(lastId, u.getId());
        }
        user.setId(lastId + 1);
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
        userRepository.save(user);
        return user;
    }


    @Cacheable(value = "security_users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
