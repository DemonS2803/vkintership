package com.example.internship.vkintership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.internship.vkintership.entities.CachedItem;
import com.example.internship.vkintership.entities.ItemTypeId;
import com.example.internship.vkintership.enums.CachedItemType;

@Repository
public interface CachedItemRepository extends JpaRepository<CachedItem, ItemTypeId> {
    
    // CachedItem findCachedItemByIdAndType(Long id, CachedItemType type);

}
