package com.example.internship.vkintership.exceptions;

import lombok.Getter;

@Getter
public class PlaceholderApiError extends RuntimeException {
    public static final String message = "Ошибка API";    
}
