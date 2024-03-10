package com.example.internship.vkintership.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.internship.vkintership.entities.cached.Album;
import com.example.internship.vkintership.services.AlbumService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAlbumById(@PathVariable Long id) {
        try {
            System.out.println("fuck yeah");
            Album AlbumData = albumService.getAlbumById(id);
            return new ResponseEntity<>(AlbumData, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllAlbums() {
        try {
            var AlbumsData = albumService.getAllAlbums();
            return new ResponseEntity<>(AlbumsData, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editAlbumById(@PathVariable("id") Long AlbumId, @RequestBody Album Album) {
        try {
            Album = albumService.updateAlbum(AlbumId, Album);
            return new ResponseEntity<>(Album, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error while updating Album");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit/create")
    public ResponseEntity<?> createAlbum(@RequestBody Album Album) {
        try {
            Album = albumService.createAlbum( Album);
            return new ResponseEntity<>(Album, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("error while creating Album");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/edit/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable("id") Long AlbumId) {
        try {
            albumService.deleteAlbum(AlbumId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("error while creating Album");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
}
