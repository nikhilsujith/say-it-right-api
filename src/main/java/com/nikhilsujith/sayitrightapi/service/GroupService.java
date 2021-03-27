package com.nikhilsujith.sayitrightapi.service;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupService {

//    Repository
    @Autowired
    GroupRepository repository;

//    Get all groups
    public List<Group> getAllGroups() {
        return repository.findAll();
    }

//  Get group by creator Name
  public Optional<List<Group>> findGroupByCreatorId(String creatorId){
      return repository.getGroupByCreatorId(creatorId);
  }

//  Get group by creator Id
//  public Optional<List<Group>> findGroupByCreatorId(String creatorId){
//      return repository.getGroupByCreatorName(creatorId);
//  }

    /*--------------------------------POST--------------------------------*/
//    Insert new group
    public String createNewGroup(Group group ){
        Group g=repository.save(group);
        return g.id;
    }


}
