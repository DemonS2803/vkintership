package com.example.internship.vkintership.utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.internship.vkintership.entities.CachedItem;
import com.example.internship.vkintership.entities.ItemTypeId;
import com.example.internship.vkintership.entities.cached.User;
import com.example.internship.vkintership.entities.cached.Album;
import com.example.internship.vkintership.entities.cached.Post;
import com.example.internship.vkintership.enums.CachedItemType;
import com.example.internship.vkintership.repositories.CachedItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ItemsCache {
    
    @Autowired
    private CachedItemRepository repository;

    ObjectMapper mapper = new ObjectMapper();



    // public boolean isPostInCache(Long postId) {
    //     ItemTypeId id = new ItemTypeId(postId, CachedItemType.POST);
    //     CachedItem post = repository.getReferenceById(id);
    //     return post != null;
    // }

    public Post getPostById(Long postId) throws JsonMappingException, JsonProcessingException {
        ItemTypeId id = new ItemTypeId(postId, CachedItemType.POST);
        Optional<CachedItem> post = repository.findById(id);
        if (post.isEmpty()) return null;
        return mapper.readValue(post.get().getData(), Post.class);
    }

    public List<Post> getAllPosts() {
        List<Post> list = repository.findAllCachedItemsByType(CachedItemType.POST.name()).stream()
                                    .map((CachedItem cached) -> {
                                        try {
                                            return mapper.readValue(cached.getData(), Post.class);
                                        } catch (Exception e) {
                                            log.error("error while validating " + cached);
                                            return null;
                                        } 
                                    })
                                    .collect(Collectors.toList());
        return  list;
    }

    public void putPost(Post post) throws JsonMappingException, JsonProcessingException {
        var id = new ItemTypeId(post.getId(), CachedItemType.POST);
        CachedItem cachedItem = CachedItem.builder()
                                            .id(id)
                                            .data(mapper.writeValueAsString(post))
                                            .lastUsed(LocalDateTime.now())
                                            .build();
        log.info("put post data in cache: " + id);
        repository.save(cachedItem);
    }

    public void putManyPosts(List<Post> posts) throws JsonMappingException, JsonProcessingException {
        for (Post post: posts) {
            putPost(post);
        }
    }

    public void deletePostById(Long postId) {
        var id = new ItemTypeId(postId, CachedItemType.POST);
        repository.deleteById(id);
    }


    public Album getAlbumById(Long postId) throws JsonMappingException, JsonProcessingException {
        ItemTypeId id = new ItemTypeId(postId, CachedItemType.ALBUM);
        Optional<CachedItem> album = repository.findById(id);
        if (album.isEmpty()) return null;
        return mapper.readValue(album.get().getData(), Album.class);
    }

    public List<Album> getAllAlbums() {
        List<Album> list = repository.findAllCachedItemsByType(CachedItemType.ALBUM.name()).stream()
                                    .map((CachedItem cached) -> {
                                        try {
                                            return mapper.readValue(cached.getData(), Album.class);
                                        } catch (Exception e) {
                                            log.error("error while validating " + cached);
                                            return null;
                                        } 
                                    })
                                    .collect(Collectors.toList());
        return  list;
    }

    public void putAlbum(Album album) throws JsonMappingException, JsonProcessingException {
        var id = new ItemTypeId(album.getId(), CachedItemType.ALBUM);
        CachedItem cachedItem = CachedItem.builder()
                                            .id(id)
                                            .data(mapper.writeValueAsString(album))
                                            .lastUsed(LocalDateTime.now())
                                            .build();
        log.info("put post data in cache: " + id);
        repository.save(cachedItem);
    }

    public void putManyAlbums(List<Album> albums) throws JsonMappingException, JsonProcessingException {
        for (Album album: albums) {
            putAlbum(album);
        }
    }

    public void deleteAlbumById(Long albumId) {
        var id = new ItemTypeId(albumId, CachedItemType.ALBUM);
        repository.deleteById(id);
    }

    // 
    // 
    // 

    public User getUserById(Long postId) throws JsonMappingException, JsonProcessingException {
        ItemTypeId id = new ItemTypeId(postId, CachedItemType.USER);
        Optional<CachedItem> user = repository.findById(id);
        if (user.isEmpty()) return null;
        return mapper.readValue(user.get().getData(), User.class);
    }

    public List<User> getAllUsers() {
        List<User> list = repository.findAllCachedItemsByType(CachedItemType.USER.name()).stream()
                                    .map((CachedItem cached) -> {
                                        try {
                                            return mapper.readValue(cached.getData(), User.class);
                                        } catch (Exception e) {
                                            log.error("error while validating " + cached);
                                            return null;
                                        } 
                                    })
                                    .collect(Collectors.toList());
        return  list;
    }

    public void putUser(User user) throws JsonMappingException, JsonProcessingException {
        var id = new ItemTypeId(user.getId(), CachedItemType.USER);
        CachedItem cachedItem = CachedItem.builder()
                                            .id(id)
                                            .data(mapper.writeValueAsString(user))
                                            .lastUsed(LocalDateTime.now())
                                            .build();
        log.info("put user data in cache: " + id);
        repository.save(cachedItem);
    }

    public void putManyUsers(List<User> users) throws JsonMappingException, JsonProcessingException {
        for (User user: users) {
            putUser(user);
        }
    }

    public void deleteUserById(Long userId) {
        var id = new ItemTypeId(userId, CachedItemType.USER);
        repository.deleteById(id);
    }

}
