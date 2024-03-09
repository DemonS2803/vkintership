package com.example.internship.vkintership.entities;

import java.time.LocalDateTime;

import com.example.internship.vkintership.enums.CachedItemType;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "cached_items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CachedItem {

    // Раз логики над сущностями не производится
    // решил засунуть все в одну таблицу
    // отошел от идеи создания отдельных классов
    @Id
    private ItemTypeId id;
    private LocalDateTime lastUsed;
    private String data;
    
}

@Embeddable
class ItemTypeId {

    private Long id;
    private CachedItemType type;
}