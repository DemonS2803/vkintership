package com.example.internship.vkintership.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllAlbums() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
