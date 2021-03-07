package com.nikhilsujith.sayitrightapi.controller;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/group")
public class GroupController {

//    Get Service
    @Autowired
    GroupService service;

    @GetMapping("/name")
    public Optional<List<Group>> getGroupByCreatorName(@RequestParam(name = "creator") String creatorName){
        return service.findGroupByCreatorName(creatorName);
    }

    @GetMapping("/id")
    public Optional<List<Group>> getGroupByCreatorId(@RequestParam(name = "creator") String creatorId){
        System.out.println("Creator ID Search");
        return service.findGroupByCreatorName(creatorId);
    }

    /*--------------------------------POST--------------------------------*/
//    FIXME
//      Group get's added, model does not parse to JSON.

    @PostMapping()
    public void createNewGroup(Group group){
        service.createNewGroup(group);
    }
}
