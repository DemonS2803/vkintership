package com.example.internship.vkintership.entities;

import java.time.LocalDateTime;

import com.example.internship.vkintership.enums.CachedItemType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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
    @EmbeddedId
    private ItemTypeId id;
    private LocalDateTime lastUsed;
    @Lob
    private JsonNode data;
    
}



