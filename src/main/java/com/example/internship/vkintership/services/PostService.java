package com.example.internship.vkintership.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.internship.vkintership.entities.cached.Post;
import com.example.internship.vkintership.exceptions.EditError;
import com.example.internship.vkintership.utils.ItemsCache;
import com.example.internship.vkintership.utils.PlaceholderApi;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostService {

    @Autowired
    private PlaceholderApi placeholderApi;

    private ObjectMapper mapper = new ObjectMapper();
    private boolean isCachedAllPost = false;

    @Cacheable("posts")
    public Post getPostById(Long postId) throws InterruptedException, IOException {
        Post data = placeholderApi.getPostById(postId);
        return data;
    }

    @Cacheable("posts")
    public List<Post> getAllPosts() throws InterruptedException, IOException {
        var postsArr = placeholderApi.getPosts();
        return postsArr;
    }

    @CachePut(value = "posts", key = "#postId")
    public Post updatePost(Long postId, Post post) throws InterruptedException, IOException {
        if (postId != post.getId()) throw new EditError();
        placeholderApi.updatePostById(postId, mapper.writeValueAsString(post));
        return post;
    }

    @CacheEvict(value = "posts")
    public void deletePost(Long postId) throws InterruptedException, IOException {
        placeholderApi.deletePostById(postId);
    }

    @CachePut(value = "posts", key = "#post.id")
    public Post createPost(Post post) throws InterruptedException, IOException {
        placeholderApi.createPost(mapper.writeValueAsString(post));
        return post;
    }
    
}
