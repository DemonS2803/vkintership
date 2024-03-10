package com.example.internship.vkintership.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.internship.vkintership.entities.SecurityAudit;
import com.example.internship.vkintership.entities.User;
import com.example.internship.vkintership.enums.AuditStatus;
import com.example.internship.vkintership.repositories.SecurityAuditRepository;


@Service
public class SecurityAuditService {
    
    @Autowired
    private SecurityAuditRepository repository;
    @Autowired 
    private SecurityUserService userService;

    public void createUserAction(AuditStatus status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
            user = userService.getUserByLogin(((UserDetails) authentication.getPrincipal()).getUsername());
        }
        
        var req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String requestUri = req.getRequestURI();
        String method = req.getMethod();
        SecurityAudit audit = SecurityAudit.builder()
                                            .status(status)
                                            .user(user)
                                            .timestamp(LocalDateTime.now())
                                            .requestUrl(requestUri)
                                            .method(method)
                                            .build();
        repository.save(audit);

    }

}
