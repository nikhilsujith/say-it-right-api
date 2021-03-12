package com.nikhilsujith.sayitrightapi.controller;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.model.User;
import com.nikhilsujith.sayitrightapi.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

//    Get Service
    @Autowired
    UserService service;

//    Get all user data
    @GetMapping("/all")
    public List<User> getAllData(){
        return service.getAllUserData();
    }

//    Get user by User ID
    @GetMapping("/{userId}")
    public Optional<User> getUserById(@PathVariable ObjectId userId){
        return service.getUserById(userId);
    }

    /*------------------------POST---------------------------*/
    @PostMapping
    public void addNewUser(@RequestBody User user){
        service.addNewUser(user);
    }
//    TODO
//        Handle E11000 duplicate key error collection from MongoDB
//          Error cause by Unique index on groupName in sayitrightdb > groups

}
