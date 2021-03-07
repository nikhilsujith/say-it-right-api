package com.nikhilsujith.sayitrightapi.controller;

import com.nikhilsujith.sayitrightapi.model.User;
import com.nikhilsujith.sayitrightapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

//    Get Service
    @Autowired
    UserService service;

//    Get all user data
    @GetMapping
    public List<User> getAllData(){
        return service.getAllUserData();
    }

//    Get user by User ID
    @GetMapping("/{userId}")
    public Optional<User> getUserById(@PathVariable String userId){
        return service.getUserById(userId);
    }

}
