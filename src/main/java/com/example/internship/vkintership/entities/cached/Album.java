package com.example.internship.vkintership.entities.cached;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    
    private Long id;
    private Long userId;
    private String title;
    

}
