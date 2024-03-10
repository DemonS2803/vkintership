package com.example.internship.vkintership;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.internship.vkintership.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class VkintershipApplicationTests {

	@Autowired
    private MockMvc mockMvc;
	@Autowired
	private UserService userService;

	// обязательно должен стоять статус DRAFT
	private final Long TESTED_STATEMENT_ID = 1L;
	private final Long TESTED_USER_ID = 2L;


}
