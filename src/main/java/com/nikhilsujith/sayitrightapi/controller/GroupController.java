package com.nikhilsujith.sayitrightapi.controller;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.model.GroupMember;
import com.nikhilsujith.sayitrightapi.service.GroupService;
import com.nikhilsujith.sayitrightapi.service.S3Service;
import com.nikhilsujith.sayitrightapi.service.UserService;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    
    @GetMapping("/users")
    public List<GroupMember> getUsersByGroupId(@RequestParam(name = "groupId") String groupId){
        System.out.println("Inside controller");
        return groupService.findUsersByGroupId(groupId);
    }

//    Delete Group
    @DeleteMapping("/delete/{id}")
    public void deleteGroupById(@PathVariable String id){
        groupService.deleteGroup(new ObjectId(id));
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

    @PostMapping("/updateGroup")
    public ResponseEntity updateGroup(@RequestBody Group group,@RequestParam("poolId") String poolId){
        String result=groupService.updateGroup(group,poolId);
        if(result=="success"){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
    
    @PostMapping("/enroll")
    public ResponseEntity enrollNewGroup(@RequestParam("group") String groupId,
                                 @RequestParam("pool") String poolId){
        String res=groupService.enrollNewGroup(groupId, poolId);
        if (res == "success") {
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

// Upload Image
    @PostMapping(
            path = "image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String uploadUserProfileImage(@RequestParam("groupId") String groupId,
                                         @RequestParam("poolId") String poolId,
                                         @RequestParam("file") MultipartFile file
    ) {
        return groupService.uploadImage(groupId, file, poolId);
    }

    @DeleteMapping("removeUser")
    public ResponseEntity removeUser(@RequestParam("c_pool") String creatorPoolId,
                             @RequestParam("group") String groupId,
                             @RequestParam("pool") String userPoolId){
        String res=groupService.removeGroupMember(creatorPoolId, groupId,userPoolId);
        if (res == "success") {
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @DeleteMapping("exitGroup")
    public ResponseEntity exitGroup(@RequestParam("group") String groupId,
                             @RequestParam("pool") String userPoolId){
        String res=groupService.exitGroup(groupId,userPoolId);
        if (res == "success") {
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @DeleteMapping("removeGroup")
    public ResponseEntity removeGroup(@RequestParam("group") String groupId,
                                    @RequestParam("pool") String userPoolId){
        String res=groupService.removeGroup(groupId,userPoolId);
        if (res == "success") {
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

}
