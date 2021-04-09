package com.nikhilsujith.sayitrightapi.service;

import com.nikhilsujith.sayitrightapi.bucket.BucketName;
import com.nikhilsujith.sayitrightapi.fileStore.FileStore;
import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.model.GroupMember;
import com.nikhilsujith.sayitrightapi.model.User;
import com.nikhilsujith.sayitrightapi.model.UserGroup;
import com.nikhilsujith.sayitrightapi.repository.GroupRepository;
import com.nikhilsujith.sayitrightapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.entity.ContentType;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    FileStore fileStore;

    @Autowired
    S3Service s3Service;

    //    Get all user data
    public List<User> getAllUserData() {
        return userRepository.findAll();
    }

    //    Get user by UserId
    public Optional<User> getUserById(String poolId) {
        ObjectId userId = getUserIdFromPoolId(poolId);
        Optional<User> user = userRepository.findById(userId);
        User uId = user.get();
        String fetchedUserId = uId.getId();
        return userRepository.findById(new ObjectId(fetchedUserId));
    }
    
	//  Get getEnrolledGroupList by pool_id
	  public List<UserGroup> findEnrolledGroupsByPoolId(String poolId){
          ObjectId user_id=getUserIdFromPoolId(poolId);
          Optional<User> u=userRepository.findById(user_id);
          return u.get().enrolledGroups;
	  }
	  
	//  Get getCreatedGroupList by pool_id
		  public List<UserGroup> findCreatedGroupsByPoolId(String poolId){
	          ObjectId user_id=getUserIdFromPoolId(poolId);
	          Optional<User> u=userRepository.findById(user_id);
	          return u.get().myGroups;
		  }

    /*--------------------------POST--------------------------------------------*/
    public void addNewUser(User user) {
        userRepository.save(user);
    }

//    update database with image link
    public String updateDatabaseImageLink(String link){
        return "updated";
    }
    
    public int insertUserCreatedGroup(Group g) {
    	Optional<User> u=userRepository.findById(new ObjectId(g.creatorId));
    	User uu=u.get();
    	int count=0;
    	UserGroup new_ug=new UserGroup();
    	new_ug.id=g.id;
    	new_ug.groupName=g.groupName;
    	new_ug.groupImage=g.groupImage;
    	new_ug.groupDesc=g.groupDesc;
    	uu.myGroups.add(new_ug);
    	userRepository.save(uu);
    	for(UserGroup x:uu.myGroups) {
    		count++;
    	}
    	return count;
    }


    /*------------------------Image---------------------------*/
    public String uploadImage(String poolId, MultipartFile file){
        return s3Service.uploadImage(poolId, file);
    }

    /* User id from pool id */
    @NotNull ObjectId getUserIdFromPoolId(String id) {
        Optional<User> fetchedUser = userRepository.getGroupByCreatorId(id);
        User user = fetchedUser.get();
        return new ObjectId(user.getId());
//        TODO
//           Handle empty case
    }


}
