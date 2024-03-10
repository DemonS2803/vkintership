package com.example.internship.vkintership.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    private ItemsCache cache;
    @Autowired
    private PlaceholderApi placeholderApi;

    private ObjectMapper mapper = new ObjectMapper();
    private boolean isCachedAllPost = false;

    public Post getPostById(Long postId) throws InterruptedException, IOException {
        Post cachedPost = cache.getPostById(postId);
        if (cachedPost == null) {
            log.info("no post " + postId + " data in cache");
            Post data = placeholderApi.getPostById(postId);
            cache.putPost(data);
            return data;
        }
        log.info("get post from cache");
        return cachedPost;
    }

    public List<Post> getAllPosts() throws InterruptedException, IOException {
        if (isCachedAllPost) {
            log.info("got many posts from cache");
            return cache.getAllPosts();
        } 
        var postsArr = placeholderApi.getPosts();
        log.info("got posts from api: " + postsArr.size());
        cache.putManyPosts(postsArr);
        this.isCachedAllPost = true;
        return postsArr;
    }

    public Post updatePost(Long postId, Post post) throws InterruptedException, IOException {
        if (postId != post.getId()) throw new EditError();

        cache.putPost(post);
        placeholderApi.updatePostById(postId, mapper.writeValueAsString(post));
        return post;
    }

    public void deletePost(Long postId) throws InterruptedException, IOException {
        cache.deletePostById(postId);
        placeholderApi.deletePostById(postId);
    }

    public Post createPost(Post post) throws InterruptedException, IOException {
        Post cachedPost = cache.getPostById(post.getId());
        if (cachedPost != null) throw new EditError();

        cache.putPost(post);
        placeholderApi.createPost(mapper.writeValueAsString(post));

        return post;

    }
    
}
