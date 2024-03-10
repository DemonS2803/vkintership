package com.example.internship.vkintership.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.internship.vkintership.enums.AuditStatus;
import com.example.internship.vkintership.services.SecurityAuditService;

import java.io.IOException;

@Component
@Slf4j
public class AuthEntryExceptionHandler implements AuthenticationEntryPoint {

    @Autowired
    private SecurityAuditService auditService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage());
        auditService.createUserAction(AuditStatus.REJECT);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}

