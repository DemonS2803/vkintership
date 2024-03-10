package com.example.internship.vkintership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.internship.vkintership.entities.SecurityAudit;

@Repository
public interface SecurityAuditRepository extends JpaRepository<SecurityAudit, Long> {
    
    

}
