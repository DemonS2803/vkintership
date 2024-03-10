package com.example.internship.vkintership.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.internship.vkintership.services.SecurityUserService;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private SecurityUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        com.example.internship.vkintership.entities.User user = userService.getUserByLogin(login);
        // user.setPassword();
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (var authority: user.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(authority.name()));
        }

        return new org.springframework.security.core.userdetails.User(user.getLogin(), passwordEncoder.encode(user.getPassword()), true, true, true, true, authorities);
    }

}

