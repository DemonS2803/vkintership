package com.example.internship.vkintership.entities.cached;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    private Address address;
    private Company company;
    
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Address {

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Geo {

    private Float lat;
    private Float lng;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Company {

    private String name;
    private String catchPhrase;
    private String bs;
}