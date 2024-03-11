package com.example.internship.vkintership.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.internship.vkintership.entities.cached.Album;
import com.example.internship.vkintership.exceptions.EditError;
import com.example.internship.vkintership.utils.ItemsCache;
import com.example.internship.vkintership.utils.PlaceholderApi;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
public class AlbumService {

    @Autowired
    private PlaceholderApi placeholderApi;

    private ObjectMapper mapper = new ObjectMapper();

    @Cacheable(value = "albums")
    public Album getAlbumById(Long albumId) throws InterruptedException, IOException {
        Album data = placeholderApi.getAlbumById(albumId);
        return data;
    }

    @Cacheable(value = "albums")
    public List<Album> getAllAlbums() throws InterruptedException, IOException {
        var albumsArr = placeholderApi.getAlbums();
        return albumsArr;
    }

    @CachePut(value = "albums", key = "#albumId")
    public Album updateAlbum(Long albumId, Album album) throws InterruptedException, IOException {
        if (albumId != album.getId()) throw new EditError();

        placeholderApi.updateAlbumById(albumId, mapper.writeValueAsString(album));
        return album;
    }

    @CacheEvict(value = "albums")
    public void deleteAlbum(Long albumId) throws InterruptedException, IOException {
        placeholderApi.deleteAlbumById(albumId);
    }

    @CachePut(value = "albums")
    public Album createAlbum(Album album) throws InterruptedException, IOException {
        placeholderApi.createAlbum(mapper.writeValueAsString(album));
        return album;
    }
    
}
