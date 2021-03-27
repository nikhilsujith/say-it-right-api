package com.nikhilsujith.sayitrightapi.controller;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.service.GroupService;
import com.nikhilsujith.sayitrightapi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/group")
public class GroupController {

//    Get Service
    @Autowired
    GroupService service;
    
//  Get Service
  @Autowired
  UserService user_service;

//    Get all groups
    @GetMapping("/all")
    public List<Group> getAllGroups(){
        return service.getAllGroups();
    }

    @GetMapping("/owner")
    public Optional<List<Group>> getGroupByName(@RequestParam(name = "id") String groupName){
        System.out.println("Inside controller");
        return service.findGroupByCreatorId(groupName);
    }

//    @GetMapping("/id")
//    public Optional<List<Group>> getGroupByCreatorId(@RequestParam(name = "creator") String creatorId){
//        System.out.println("Creator ID Search");
//        return service.findGroupByCreatorName(creatorId);
//    }

    /*--------------------------------POST--------------------------------*/
//    FIXME
//      Group get's added, model does not parse to JSON.

    @PostMapping()
    public String createNewGroup(@RequestBody Group group){
        String obj_id=service.createNewGroup(group);
        int r=user_service.insertUserCreatedGroup(group);
        return String.valueOf(r);
    }
}
