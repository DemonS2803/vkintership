package com.example.internship.vkintership.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.example.internship.vkintership.enums.AuditStatus;
import com.example.internship.vkintership.services.SecurityAuditService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthEntryPointSuccess extends HttpFilter {
    
    @Autowired
    private SecurityAuditService auditService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);

        auditService.createUserAction(AuditStatus.SUCCESS);
        
    }

}


