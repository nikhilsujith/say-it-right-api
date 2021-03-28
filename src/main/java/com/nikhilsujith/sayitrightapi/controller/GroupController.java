package com.nikhilsujith.sayitrightapi.controller;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.model.GroupMember;
import com.nikhilsujith.sayitrightapi.service.GroupService;
import com.nikhilsujith.sayitrightapi.service.UserService;
import org.bson.types.Binary;
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
    GroupService groupService;
    
    @Autowired
    UserService userService;

//    Get all groups
    @GetMapping("/all")
    public List<Group> getAllGroups(){
        return groupService.getAllGroups();
    }

    @GetMapping("/owner")
    public Optional<List<Group>> getGroupByName(@RequestParam(name = "id") String groupName){
        System.out.println("Inside controller");
        return groupService.findGroupByCreatorId(groupName);
    }
    
    @GetMapping("/getUsers")
    public List<GroupMember> UsersByGroupId(@RequestParam(name = "id") String groupId){
        System.out.println("Inside controller");
        return groupService.findUsersByGroupId(groupId);
    }

//    @GetMapping("/id")
//    public Optional<List<Group>> getGroupByCreatorId(@RequestParam(name = "creator") String creatorId){
//        System.out.println("Creator ID Search");
//        return service.findGroupByCreatorName(creatorId);
//    }

    /*--------------------------------POST--------------------------------*/
//    FIXME
//      Group get's added, model does not parse to JSON.

//    @PostMapping()
//    public void createNewGroup(@RequestBody Group group){
//        service.createNewGroup(group);
//    }
    
    @PostMapping()
    public String createNewGroup(@RequestBody Group group){
        String obj_id=groupService.createNewGroup(group);
        int r=userService.insertUserCreatedGroup(group);
        //return String.valueOf(r);
        return String.valueOf(obj_id);
    }
    
    @PostMapping("/enrollNewGroup")
    public String enrollNewGroup(HttpServletRequest request){
    	String grp_id=request.getHeader("grpId");
    	String pool_id=request.getHeader("poolId");
    	String res=groupService.enrollNewGroup(grp_id, pool_id);
    	return res;
    }
}
