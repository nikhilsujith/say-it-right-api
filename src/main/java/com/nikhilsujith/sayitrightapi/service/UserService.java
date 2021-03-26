package com.nikhilsujith.sayitrightapi.service;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.model.User;
import com.nikhilsujith.sayitrightapi.model.UserGroup;
import com.nikhilsujith.sayitrightapi.repository.GroupRepository;
import com.nikhilsujith.sayitrightapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserService {

//    Get Repository
    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

//    Get all user data
    public List<User> getAllUserData(){
        return userRepository.findAll();
    }

//    Get user by UserId
    public Optional<User> getUserById(ObjectId userId){
        return userRepository.findById(userId);
    }

    /*--------------------------POST--------------------------------------------*/
    public void addNewUser(User user){
       userRepository.insert(user);


//        repository.save(group);
//        TODO
//            Need to save group reference
//                Help At:
//            https://stackoverflow.com/questions/51943411/cannot-create-a-reference-to-an-object-with-a-null-id-mongo-hibernate-mongo-and
//              https://stackoverflow.com/questions/43757776/save-document-with-dbref-in-mongodb-spring-data
    }
    
    public int insertUserCreatedGroup(Group g) {
    	Optional<User> u=userRepository.findById(new ObjectId(g.creatorId));
    	User uu=u.get();
    	int count=0;
    	UserGroup new_ug=new UserGroup();
    	new_ug.id=g.id;
    	new_ug.groupName=g.groupName;
    	new_ug.groupImage=g.groupImage;
    	uu.myGroups.add(new_ug);
    	userRepository.save(uu);
    	for(UserGroup x:uu.myGroups) {
    		count++;
    	}
    	return count;
    }

}
