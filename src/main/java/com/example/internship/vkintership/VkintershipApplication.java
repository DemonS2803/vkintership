package com.example.internship.vkintership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VkintershipApplication {

	public static void main(String[] args) {
		SpringApplication.run(VkintershipApplication.class, args);
	}

}
