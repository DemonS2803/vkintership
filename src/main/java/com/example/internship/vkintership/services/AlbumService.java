package com.example.internship.vkintership.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.internship.vkintership.entities.cached.Album;
import com.example.internship.vkintership.exceptions.EditError;
import com.example.internship.vkintership.utils.ItemsCache;
import com.example.internship.vkintership.utils.PlaceholderApi;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlbumService {

    @Autowired
    private ItemsCache cache;
    @Autowired
    private PlaceholderApi placeholderApi;

    private ObjectMapper mapper = new ObjectMapper();
    private boolean isCachedAllAlbums = false;

    @Cacheable("albums")
    public Album getAlbumById(Long albumId) throws InterruptedException, IOException {
        Album cachedAlbum = cache.getAlbumById(albumId);
        if (cachedAlbum == null) {
            log.info("no Album " + albumId + " data in cache");
            Album data = placeholderApi.getAlbumById(albumId);
            cache.putAlbum(data);
            return data;
        }
        log.info("get Album from cache");
        return cachedAlbum;
    }

    public List<Album> getAllAlbums() throws InterruptedException, IOException {
        if (isCachedAllAlbums) {
            log.info("got many Albums from cache");
            return cache.getAllAlbums();
        } 
        var AlbumsArr = placeholderApi.getAlbums();
        log.info("got Albums from api: " + AlbumsArr.size());
        cache.putManyAlbums(AlbumsArr);
        this.isCachedAllAlbums = true;
        return AlbumsArr;
    }

    public Album updateAlbum(Long AlbumId, Album Album) throws InterruptedException, IOException {
        if (AlbumId != Album.getId()) throw new EditError();

        cache.putAlbum(Album);
        placeholderApi.updateAlbumById(AlbumId, mapper.writeValueAsString(Album));
        return Album;
    }

    public void deleteAlbum(Long AlbumId) throws InterruptedException, IOException {
        cache.deleteAlbumById(AlbumId);
        placeholderApi.deleteAlbumById(AlbumId);
    }

    public Album createAlbum(Album Album) throws InterruptedException, IOException {
        Album cachedAlbum = cache.getAlbumById(Album.getId());
        if (cachedAlbum != null) throw new EditError();

        cache.putAlbum(Album);
        placeholderApi.createAlbum(mapper.writeValueAsString(Album));

        return Album;
    }
    
}
