package com.example.internship.vkintership.utils;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.internship.vkintership.entities.CachedItem;
import com.example.internship.vkintership.entities.ItemTypeId;
import com.example.internship.vkintership.enums.CachedItemType;
import com.example.internship.vkintership.repositories.CachedItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
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

    public CachedItem getPostById(Long postId) {
        ItemTypeId id = new ItemTypeId(postId, CachedItemType.POST);
        Optional<CachedItem> post = repository.findById(id);
        if (post.isEmpty()) return null;
        return post.get();
    }

    public void putPost(String postData) throws JsonMappingException, JsonProcessingException {
        JsonNode node = mapper.readTree(postData);
        CachedItem cachedItem = CachedItem.builder()
                                            .id(new ItemTypeId(node.get("id").asLong(), CachedItemType.POST))
                                            .data(postData)
                                            .lastUsed(LocalDateTime.now())
                                            .build();
        log.info("put post data in cache");
        repository.save(cachedItem);

    }


}
