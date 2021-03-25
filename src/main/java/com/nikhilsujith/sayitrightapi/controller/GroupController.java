package com.nikhilsujith.sayitrightapi.controller;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.service.GroupService;
import org.bson.types.Binary;
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
    public void createNewGroup(@RequestBody Group group){
        service.createNewGroup(group);
    }
}
