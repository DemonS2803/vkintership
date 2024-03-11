package com.example.internship.vkintership.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.internship.vkintership.entities.cached.User;
import com.example.internship.vkintership.exceptions.EditError;
import com.example.internship.vkintership.utils.ItemsCache;
import com.example.internship.vkintership.utils.PlaceholderApi;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
public class UserService {

    @Autowired
    private PlaceholderApi placeholderApi;

    private ObjectMapper mapper = new ObjectMapper();

    @Cacheable("users")
    public User getUserById(Long userId) throws InterruptedException, IOException {

        User data = placeholderApi.getUserById(userId);
        return data;
    }

    @Cacheable("users")
    public List<User> getAllUsers() throws InterruptedException, IOException {
        var usersArr = placeholderApi.getUsers();
        return usersArr;
    }


    @CachePut(value = "users", key = "#userId")
    public User updateUser(Long userId, User user) throws InterruptedException, IOException {
        if (userId != user.getId()) throw new EditError();
        placeholderApi.updateUserById(userId, mapper.writeValueAsString(user));
        return user;
    }

    @CacheEvict("users")
    public void deleteUser(Long userId) throws InterruptedException, IOException {
        placeholderApi.deleteUserById(userId);
    }

    @CachePut(value = "users", key = "#user.id")
    public User createUser(User user) throws InterruptedException, IOException {
        placeholderApi.createUser(mapper.writeValueAsString(user));

        return user;

    }
    
}
