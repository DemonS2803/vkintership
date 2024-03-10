package com.example.internship.vkintership.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.example.internship.vkintership.entities.cached.User;
import com.example.internship.vkintership.entities.cached.Album;
import com.example.internship.vkintership.entities.cached.Post;
import com.example.internship.vkintership.exceptions.PlaceholderApiError;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PlaceholderApi {

    public static final String apiUrl = "https://jsonplaceholder.typicode.com";
    
    private HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private ObjectMapper mapper = new ObjectMapper();
    

    // POSTS
    public Post getPostById(Long id) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/posts/" + id))
            .GET().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        Post post = mapper.readValue(response.body(), Post.class);
        log.info("get post " + id + " from api");
        return post;
    }

    public ArrayList<Post> getPosts() throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/posts"))
            .GET().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        ArrayList<Post> list = new ArrayList<>();
        for (Post post: mapper.readValue(response.body(), Post[].class)) {
            list.add(post);
        }
        return list;
    }

    public void createPost(String postData) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/posts"))
            .POST(HttpRequest.BodyPublishers.ofString(postData))
            .build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) throw new PlaceholderApiError();
        
    }

    public String updatePostById(Long id, String postData) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/posts/" + id))
            .PUT(HttpRequest.BodyPublishers.ofString(postData))
            .build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public void deletePostById(Long id) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/posts/" + id))
            .DELETE().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new PlaceholderApiError();
    }


    // ALBUMS
    public Album getAlbumById(Long id) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/albums/" + id))
            .GET().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        Album album = mapper.readValue(response.body(), Album.class);
        log.info("got album " + album + " from api");
        return album;
    }

    public ArrayList<Album> getAlbums() throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/albums"))
            .GET().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        ArrayList<Album> list = new ArrayList<>();
        for (Album album: mapper.readValue(response.body(), Album[].class)) {
            list.add(album);
        }
        log.info("got " + list.size() + " albums from api");
        return list;
    }

    public void createAlbum(String albumData) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/albums"))
            .POST(HttpRequest.BodyPublishers.ofString(albumData))
            .build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) throw new PlaceholderApiError();
        
    }

    public String updateAlbumById(Long id, String albumData) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/albums/" + id))
            .PUT(HttpRequest.BodyPublishers.ofString(albumData))
            .build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public void deleteAlbumById(Long id) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/albums/" + id))
            .DELETE().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new PlaceholderApiError();
    }


    // USERS
    public User getUserById(Long id) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/users/" + id))
            .GET().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        User user = mapper.readValue(response.body(), User.class);
        log.info("got user from api");
        return user;
    }

    public ArrayList<User> getUsers() throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/users"))
            .GET().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        ArrayList<User> list = new ArrayList<>();
        for (User user: mapper.readValue(response.body(), User[].class)) {
            list.add(user);
        }
        log.info("got " + list.size() + " users from api");
        return list;
    }

    public void createUser(String userData) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/users"))
            .POST(HttpRequest.BodyPublishers.ofString(userData))
            .build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) throw new PlaceholderApiError();
        
    }

    public String updateUserById(Long id, String userData) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/users/" + id))
            .PUT(HttpRequest.BodyPublishers.ofString(userData))
            .build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public void deleteUserById(Long id) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/users/" + id))
            .DELETE().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new PlaceholderApiError();
    }
    
}
