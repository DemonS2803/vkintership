package com.example.internship.vkintership.controllers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.internship.vkintership.dto.AuthDTO;
import com.example.internship.vkintership.entities.User;
import com.example.internship.vkintership.security.JwtUtils;
import com.example.internship.vkintership.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.time.access-expired}")
    private Integer accessTokenExpirationTime;
    @Value("${jwt.time.refresh-expired}")
    private Integer refreshTokenExpirationTime;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Validated AuthDTO loginRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        try {
            User user = userService.getUserByLogin(loginRequest.getLogin());

            authenticateUser(loginRequest.getLogin(), DigestUtils.sha256Hex(loginRequest.getPassword()));
            String accessJwt = jwtUtils.generateJwtToken(user, accessTokenExpirationTime);

            log.info("success login with credentials: " + loginRequest);
            return new ResponseEntity<>(new AuthDTO("", "", accessJwt), HttpStatus.ACCEPTED);

        } catch (Exception e) { 
            log.warn("invalid login with credentials: " + loginRequest, e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication authenticateUser(String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
    
}
