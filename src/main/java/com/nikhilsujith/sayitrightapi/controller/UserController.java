package com.nikhilsujith.sayitrightapi.controller;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.model.User;
import com.nikhilsujith.sayitrightapi.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    //    Get Service
    @Autowired
    UserService service;

    //    Get all user data
    @GetMapping("/all")
    public List<User> getAllData() {
        return service.getAllUserData();
    }

    //    Get user by User ID
    @GetMapping("/{userId}")
    public Optional<User> getUserById(@PathVariable ObjectId userId) {
        return service.getUserById(userId);
    }

    /*------------------------POST---------------------------*/
    @PostMapping
    public ResponseEntity addNewUser(@RequestBody User user){
        service.addNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
//    TODO
//        Handle E11000 duplicate key error collection from MongoDB
//          Error cause by Unique index on groupName in sayitrightdb > groups

    @PostMapping(
            path = "{id}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String uploadUserProfileImage(@PathVariable("id") String id,
                                         @RequestParam("file") MultipartFile file
    ) {
        return service.uploadImage(id, file);
    }

}
