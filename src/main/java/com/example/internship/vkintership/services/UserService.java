package com.example.internship.vkintership.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.internship.vkintership.entities.cached.User;
import com.example.internship.vkintership.exceptions.EditError;
import com.example.internship.vkintership.utils.ItemsCache;
import com.example.internship.vkintership.utils.PlaceholderApi;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {


    @Autowired
    private ItemsCache cache;
    @Autowired
    private PlaceholderApi placeholderApi;

    private ObjectMapper mapper = new ObjectMapper();
    private boolean isCachedAllUsers = false;



    public User getUserById(Long userId) throws InterruptedException, IOException {
        User cachedUser = cache.getUserById(userId);
        if (cachedUser == null) {
            log.info("no user " + userId + " data in cache");
            User data = placeholderApi.getUserById(userId);
            cache.putUser(data);
            return data;
        }
        log.info("get user from cache");
        return cachedUser;
    }

    public List<User> getAllUsers() throws InterruptedException, IOException {
        if (isCachedAllUsers) {
            log.info("got many users from cache");
            return cache.getAllUsers();
        } 
        var usersArr = placeholderApi.getUsers();
        log.info("got users from api: " + usersArr.size());
        cache.putManyUsers(usersArr);
        this.isCachedAllUsers = true;
        return usersArr;
    }

    public User updateUser(Long userId, User user) throws InterruptedException, IOException {
        if (userId != user.getId()) throw new EditError();

        cache.putUser(user);
        placeholderApi.updateUserById(userId, mapper.writeValueAsString(user));
        return user;
    }

    public void deleteUser(Long userId) throws InterruptedException, IOException {
        cache.deleteUserById(userId);
        placeholderApi.deleteUserById(userId);
    }

    public User createUser(User user) throws InterruptedException, IOException {
        User cachedUser = cache.getUserById(user.getId());
        if (cachedUser != null) throw new EditError();

        cache.putUser(user);
        placeholderApi.createUser(mapper.writeValueAsString(user));

        return user;

    }
    
}
