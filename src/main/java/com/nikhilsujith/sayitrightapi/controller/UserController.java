package com.nikhilsujith.sayitrightapi.controller;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.model.User;
import com.nikhilsujith.sayitrightapi.model.UserGroup;
import com.nikhilsujith.sayitrightapi.repository.UserRepository;
import com.nikhilsujith.sayitrightapi.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin("*") //needs to change before going into production
public class UserController {

    //    Get Service
    @Autowired
    UserService service;

    @Autowired
    UserRepository repository;

    //    Get all user data
    @GetMapping("/all")
    public List<User> getAllData() {
        return service.getAllUserData();
    }

    //    Get user by User ID
    @GetMapping("/{poolId}")
    public Optional<User> getUserById(@PathVariable String poolId) {
        return service.getUserById(poolId);
    }

    //  Get enrolledGroupsList by Pool ID
    @GetMapping("/enrolled")
    public List<UserGroup> getEnrolledGroupsByPoolId(@RequestParam(name = "id") String poolId) {
        return service.findEnrolledGroupsByPoolId(poolId);
    }

    //Get user by User ID
    @GetMapping("/created")
    public List<UserGroup> getCreatedGroupsByPoolId(@RequestParam(name = "id") String poolId) {
        return service.findCreatedGroupsByPoolId(poolId);
    }

    //----------------Deep's getUserByPoolId------------------------

////  Get user by Pool ID
//	  @GetMapping("getUserByPoolId/{pool_id}")
//	  public User getUserById(@PathVariable String pool_id) {
//	      return service.getUserByPoolId(pool_id);
//	  }
//	  
//	//  Get userid by Pool ID
//	  @GetMapping("getUserIdByPoolId/{pool_id}")
//	  public String getUserIdById(@PathVariable String pool_id) {
//	      return service.getUserIdByPoolId(pool_id);
//	  }

    //----------------------------------------------------------------

    /*------------------------POST---------------------------*/
    @PostMapping("/addUser")
    public ResponseEntity addNewUser(@RequestBody User user) {
        service.addNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/updateUser")
    public ResponseEntity updateUser(@RequestBody User user) {
        String result=service.updateUser(user);
        if(result=="success"){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @PostMapping
//    public void addNewUser(@RequestBody User user) {
//        service.addNewUser(user);
//    }
//    TODO
//        Handle E11000 duplicate key error collection from MongoDB
//          Error cause by Unique index on groupName in sayitrightdb > groups

//    /*------------------------Image---------------------------*/

    @PostMapping(
            path = "image/upload/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String uploadUserProfileImage(@PathVariable("id") String poolId,
                                         @RequestParam("file") MultipartFile file
    ) {
        return service.uploadImage(poolId, file);
    }

    //    /*------------------------File---------------------------*/
    @PostMapping(
            path = "video/upload/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String uploadVideo(@PathVariable("id") String id,
                                         @RequestParam("file") MultipartFile file
    ) {
        return service.uploadVideo(id, file);
    }

    @DeleteMapping("removeGroup")
    public String removeUser(@RequestParam("c_pool") String creatorPoolId,
                             @RequestParam("group") String groupId){
        return service.removeGroup(creatorPoolId, groupId);
    }
    

}
