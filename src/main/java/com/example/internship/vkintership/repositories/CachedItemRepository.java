package com.example.internship.vkintership.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.internship.vkintership.entities.CachedItem;
import com.example.internship.vkintership.entities.ItemTypeId;

@Repository
public interface CachedItemRepository extends JpaRepository<CachedItem, ItemTypeId> {
    
    @Query(nativeQuery = true, value = "select * from cached_items where type = ?1")
    List<CachedItem> findAllCachedItemsByType(String type);

}
