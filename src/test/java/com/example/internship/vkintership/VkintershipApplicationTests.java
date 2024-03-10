package com.example.internship.vkintership;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.internship.vkintership.enums.UserAuthority;
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
	private final Long TESTED_USER_ID = 1L;

	@Test
    @WithMockUser(username = "user", password = "password", authorities = "ROLE_POSTS_VIEWER")
    void userPostViewerRoleCheck() throws Exception {
        mockMvc.perform(get("/api/v1/posts/get/all")).andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/posts/get/1")).andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/posts/edit/1")).andExpect(status().isForbidden());
		mockMvc.perform(get("/api/v1/albums/get/all")).andExpect(status().isForbidden());
		mockMvc.perform(get("/api/v1/users/get/all")).andExpect(status().isForbidden());
    }

	@Test
    @WithMockUser(username = "user", password = "password", authorities = {"ROLE_POSTS_VIEWER", "ROLE_POSTS_EDITOR"})
    void userPostViewerEditorRoleCheck() throws Exception {
        mockMvc.perform(get("/api/v1/posts/get/all")).andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/posts/get/1")).andExpect(status().isOk());
		// 405 потому что надо тело запроса составить
		mockMvc.perform(get("/api/v1/posts/edit/1")).andExpect(status().isMethodNotAllowed());
		mockMvc.perform(get("/api/v1/albums/get/all")).andExpect(status().isForbidden());
		mockMvc.perform(get("/api/v1/users/get/all")).andExpect(status().isForbidden());
    }

	@Test
    @WithMockUser(username = "admin", password = "password", authorities = {"ROLE_ADMIN"})
    void adminRoleCheck() throws Exception {
        mockMvc.perform(get("/api/v1/posts/get/all")).andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/posts/get/1")).andExpect(status().isOk());
		// 405 потому что надо тело запроса составить
		mockMvc.perform(get("/api/v1/posts/edit/1")).andExpect(status().isMethodNotAllowed());
		mockMvc.perform(get("/api/v1/albums/get/all")).andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/users/get/all")).andExpect(status().isOk());
    }

}
