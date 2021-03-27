package com.nikhilsujith.sayitrightapi.controller;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.service.GroupService;
import com.nikhilsujith.sayitrightapi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/group")
public class GroupController {

//    Get Service
    @Autowired
    GroupService grp_service;
    
//  Get Service
  @Autowired
  UserService user_service;

//    Get all groups
    @GetMapping("/all")
    public List<Group> getAllGroups(){
        return grp_service.getAllGroups();
    }

    @GetMapping("/owner")
    public Optional<List<Group>> getGroupByName(@RequestParam(name = "id") String groupName){
        System.out.println("Inside controller");
        return grp_service.findGroupByCreatorId(groupName);
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
        String obj_id=grp_service.createNewGroup(group);
        int r=user_service.insertUserCreatedGroup(group);
        //return String.valueOf(r);
        return String.valueOf(obj_id);
    }
    
    @PostMapping("/enrollNewGroup")
    public String enrollNewGroup(HttpServletRequest request){
    	String pool_id=request.getHeader("grpId");
    	String user_id=request.getHeader("userId");
    	String res=grp_service.enrollNewGroup(pool_id, user_id);
    	return res;
    }
}
