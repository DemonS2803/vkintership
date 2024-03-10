package com.example.internship.vkintership.entities.cached;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private Long userId;
    private Long id;
    private String title;
    private String body;
    
}
