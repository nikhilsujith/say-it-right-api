package com.nikhilsujith.sayitrightapi.service;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.model.User;
import com.nikhilsujith.sayitrightapi.model.UserGroup;
import com.nikhilsujith.sayitrightapi.model.GroupMember;
import com.nikhilsujith.sayitrightapi.repository.GroupRepository;
import com.nikhilsujith.sayitrightapi.repository.UserRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupService {

//    Repository
    @Autowired
    GroupRepository grp_repository;
    
    @Autowired
    UserRepository usr_repository;

//    Get all groups
    public List<Group> getAllGroups() {
        return grp_repository.findAll();
    }

//  Get group by creator Name
  public Optional<List<Group>> findGroupByCreatorId(String creatorId){
      return grp_repository.getGroupByCreatorId(creatorId);
  }

//  Get group by creator Id
//  public Optional<List<Group>> findGroupByCreatorId(String creatorId){
//      return repository.getGroupByCreatorName(creatorId);
//  }

    /*--------------------------------POST--------------------------------*/
//    Insert new group
    public String createNewGroup(Group group ){
        Group g=grp_repository.save(group);
        return g.id;
    }
    
    public String enrollNewGroup(String group_id,String user_id){
    	//return "group_id:"+group_id+", user_id:"+user_id;
    	
    	try {
            Optional<Group> g=grp_repository.findById(new ObjectId(group_id));
            Optional<User> u=usr_repository.findById(new ObjectId(user_id));
            
            UserGroup ug=new UserGroup();
            ug.id=g.get().id;
            ug.groupName=g.get().groupName;
            ug.groupImage=g.get().groupImage;
            ug.groupDesc=g.get().groupDesc;
            u.get().enrolledGroups.add(ug);
            
            
            GroupMember gm=new GroupMember();
            gm.id=u.get().id;
            gm.fullName=u.get().fullName;
            gm.poolId=u.get().poolId;
            gm.profileImage=u.get().profileImage;
            g.get().users.add(gm);
            
            usr_repository.save(u.get());
            grp_repository.save(g.get());
            
            return "success";
    	}
    	catch(Exception ex) {
    		return ex.toString();
    	}

    }


}
