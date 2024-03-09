package com.example.internship.vkintership.entities;

import java.util.ArrayList;

import org.hibernate.mapping.List;
import org.hibernate.mapping.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String login;
    private String password;
    @ElementCollection(targetClass = UserAuthority.class)
    @CollectionTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private ArrayList<UserAuthority> authorities;
    
}

enum UserAuthority {

    ROLE_POSTS_VIEWER,
    ROLE_POSTS_EDITOR,
    ROLE_ALBUMS_VIEWER,
    ROLE_ALBUMS_EDITOR,
    ROLE_USERS_VIEWER,
    ROLE_USERS_EDITOR,
    ROLE_ADMIN
    
}
