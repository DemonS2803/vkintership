package com.example.internship.vkintership.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.internship.vkintership.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    User findUserByLogin(String login);

}
