package com.example.internship.vkintership.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.internship.vkintership.entities.CachedItem;
import com.example.internship.vkintership.utils.ItemsCache;
import com.example.internship.vkintership.utils.PlaceholderApi;
import com.fasterxml.jackson.databind.JsonNode;
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

    public JsonNode getPostById(Long postId) throws InterruptedException, IOException {
        CachedItem cachedPost = cache.getPostById(postId);
        if (cachedPost == null) {
            log.info("no post " + postId + " data in cache");
            String data = placeholderApi.getPostById(postId);
            cache.putPost(data);
            return data;
        }
        log.info("get post from cache");
        return cachedPost.getData();
    }

    public ArrayList<JsonNode> getAllPosts() throws InterruptedException, IOException {
        String postListData = placeholderApi.getPosts();
        JsonNode node = mapper.readTree(postListData);
        ArrayList<JsonNode> list = (ArrayList<JsonNode>) StreamSupport.stream(node.spliterator(), false)
        .map((JsonNode n) -> {
            cache.putPost(n.);
            return ;
        } )
        .collect(Collectors.toList());

        log.info(list.toString());


        log.info("got posts: " + list.size());
        return postList;
    }
    
}
