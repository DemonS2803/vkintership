package com.example.internship.vkintership;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.internship.vkintership.entities.cached.User;
import com.example.internship.vkintership.enums.UserAuthority;
import com.example.internship.vkintership.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class VkintershipApplicationTests {

	@Autowired
    private MockMvc mockMvc;
	@Autowired
	private UserService userService;

	ObjectMapper mapper = new ObjectMapper();

	String TEST_USER_DATA_ID5 = "{\"id\":5,\"name\":\"ChelseyDietrich\",\"username\":\"Kamren\",\"email\":\"Lucio_Hettinger@annie.ca\",\"address\":{\"street\":\"SkilesWalks\",\"suite\":\"Suite351\",\"city\":\"Roscoeview\",\"zipcode\":\"33263\",\"geo\":{\"lat\":\"-31.8129\",\"lng\":\"62.5342\"}},\"phone\":\"(254)954-1289\",\"website\":\"demarco.info\",\"company\":{\"name\":\"KeeblerLLC\",\"catchPhrase\":\"User-centricfault-tolerantsolution\",\"bs\":\"revolutionizeend-to-endsystems\"}}";
	String TEST_POST_DATA_ID2 = "{\"userId\":1,\"id\":2,\"title\":\"quiestesse\",\"body\":\"estrerumtemporevitae\\nsequisintnihilreprehenderitdolorbeataeeadoloresneque\\nfugiatblanditiisvoluptateporrovelnihilmolestiaeutreiciendis\\nquiaperiamnondebitispossimusquinequenisinulla\"}";
	String TEST_ALBUM_DATA_ID8 = "{\"userId\":1,\"id\":8,\"title\":\"quifugaestaeum\"}";


	@Test
    @WithMockUser(username = "user", password = "password", authorities = {"ROLE_POSTS_VIEWER"})
    void userPostViewerRoleCheck() throws Exception {
        mockMvc.perform(get("/api/v1/posts/get/all")).andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/posts/get/1")).andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/posts/edit/1")).andExpect(status().isForbidden());
		mockMvc.perform(get("/api/v1/albums/get/all")).andExpect(status().isForbidden());
		mockMvc.perform(get("/api/v1/users/get/all")).andExpect(status().isForbidden());
    }

	@Test
    @WithMockUser(username = "user", password = "password", authorities = {"ROLE_POSTS_VIEWER", "ROLE_POSTS_EDITOR"})
    void userPostViewerAndEditorAuthoritiesCheck() throws Exception {
        mockMvc.perform(get("/api/v1/posts/get/all")).andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/posts/get/1")).andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/posts/edit/2").content(TEST_POST_DATA_ID2).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isMethodNotAllowed());

		mockMvc.perform(get("/api/v1/albums/get/all")).andExpect(status().isForbidden());
		mockMvc.perform(get("/api/v1/users/get/all")).andExpect(status().isForbidden());
    }

	@Test
    @WithMockUser(username = "user", password = "password", authorities = {"ROLE_USERS_VIEWER", "ROLE_USERS_EDITOR"})
    void userUserViewerAndEditorAuthoritiesCheck() throws Exception {
        mockMvc.perform(get("/api/v1/posts/get/all")).andExpect(status().isForbidden());
		mockMvc.perform(get("/api/v1/posts/get/1")).andExpect(status().isForbidden());
		mockMvc.perform(get("/api/v1/posts/edit/1")).andExpect(status().isForbidden());
		mockMvc.perform(get("/api/v1/albums/get/all")).andExpect(status().isForbidden());

		mockMvc.perform(get("/api/v1/users/get/1")).andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/users/get/all")).andExpect(status().isOk());

		mockMvc.perform(put("/api/v1/users/edit/5").content(TEST_USER_DATA_ID5).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
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

	@Test
    @WithMockUser(username = "admin", password = "password", authorities = {"ROLE_POSTS_VIEWER", "ROLE_POSTS_EDITOR", "ROLE_ALBUMS_VIEWER", "ROLE_ALBUMS_EDITOR". "ROLE_USERS_VIEWER", "ROLE_USERS_EDITOR"})
    void userAllAuthoritiesCheck() throws Exception {
        mockMvc.perform(get("/api/v1/posts/get/all")).andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/posts/get/1")).andExpect(status().isOk());
		// 405 потому что надо тело запроса составить
		mockMvc.perform(get("/api/v1/posts/edit/1")).andExpect(status().isMethodNotAllowed());
		mockMvc.perform(get("/api/v1/albums/get/all")).andExpect(status().isOk());
		mockMvc.perform(get("/api/v1/users/get/all")).andExpect(status().isOk());
    }

	

}
