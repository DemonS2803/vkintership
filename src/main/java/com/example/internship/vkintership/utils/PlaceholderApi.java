package com.example.internship.vkintership.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublisher;
import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.hibernate.mapping.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.internship.vkintership.exceptions.PlaceholderApiError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PlaceholderApi {

    public static final String apiUrl = "https://jsonplaceholder.typicode.com";
    
    private HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    

    // POSTS
    public String getPostById(Long id) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/posts/" + id))
            .GET().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        log.info("get post " + id + " from api");
        return response.body();
    }

    public String getPosts() throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/posts"))
            .GET().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        
        return response.body();
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



    // ALBUMS
    public String getAlbumById(Long id) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/albums/" + id))
            .GET().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getAlbums() throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/albums"))
            .GET().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }



    // USERS
    public String getUserById(Long id) throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/users/" + id))
            .GET().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getUsers() throws InterruptedException, IOException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + "/users"))
            .GET().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    
}
