package com.example.internship.vkintership.entities;

import java.io.Serializable;

import com.example.internship.vkintership.enums.CachedItemType;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ItemTypeId implements Serializable {

    private Long id;
    @Enumerated(EnumType.STRING)
    private CachedItemType type;

}
