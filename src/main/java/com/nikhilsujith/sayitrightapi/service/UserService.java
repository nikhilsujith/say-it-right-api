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
    GroupService groupService;

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

    public String updateUser(User user) {
        //Optional<User> u=userRepository.findById(new ObjectId(user.id));
        try{
            User u=new User();
            u=user;

            //update username in group if that was user's created group
            for(int i=0;i<u.myGroups.size();i++){
                String grpId=user.myGroups.get(i).id;
                Optional<Group> group = groupRepository.findById(new ObjectId(grpId));
                if(!u.fullName.equals(group.get().creatorName)){
                    group.get().creatorName=u.fullName;
                    groupRepository.save(group.get());
                }
            }

            for(int i=0;i<u.enrolledGroups.size();i++){
                String grpId=user.enrolledGroups.get(i).id;
                Optional<Group> group = groupRepository.findById(new ObjectId(grpId));
                for(int j=0;j<group.get().users.size();j++){
                    String userid=group.get().users.get(j).id;
                    if(userid.equals(u.id)){
                        group.get().users.get(j).fullName=u.fullName;
                        group.get().users.get(j).profileImage=u.profileImage;
                    }
                }
                groupRepository.save(group.get());
            }

            userRepository.save(u);

//            for(UserGroup v1:u.enrolledGroups){
//                Optional<Group> g=groupRepository.findById(new ObjectId(v1.id));
//                List<GroupMember> gm=g.get().users;
//                for(GroupMember v2:gm){
//                    if(u.poolId.equals(v2.poolId)){
//                        v2.fullName=u.fullName;
//                        v2.profileImage=u.profileImage;
//                        v2.id=u.id;
//                        v2.poolId=u.id
//                    }
//                }
//
//            }

            return "success";
        }
        catch(Exception ex) {
            return ex.toString();
        }

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
        String ImageResponse =  s3Service.uploadImage(poolId, file);
        updateImageLink(poolId, ImageResponse);
        updateImageLinkInGroup(poolId, ImageResponse);
        return ImageResponse;
    }

    /*------------------------Video---------------------------*/
    public String uploadVideo(String poolId, MultipartFile file) {
        String VideoResponse =  s3Service.uploadVideo(poolId, file);
        updateUserVideoLink(poolId, VideoResponse);
        return VideoResponse;
    }

    /* User id from pool id */
    @NotNull ObjectId getUserIdFromPoolId(String id) {
        Optional<User> fetchedUser = userRepository.getGroupByCreatorId(id);
        User user = fetchedUser.get();
        return new ObjectId(user.getId());
//        TODO
//           Handle empty case
    }

    private void updateUserVideoLink(String poolId, String response){
        ObjectId oId = getUserIdFromPoolId(poolId);
        Optional<User> user = userRepository.findById(oId);
        User updatedUser = user.get();
        updatedUser.setVideoFile(response);
        userRepository.save(updatedUser);
    }

    private void updateImageLink(String poolId, String response) {
        ObjectId oId = getUserIdFromPoolId(poolId);
        Optional<User> user = userRepository.findById(oId);
        User updatedUser = user.get();
        updatedUser.setProfileImage(response);
        userRepository.save(updatedUser);
    }

    private void updateImageLinkInGroup(String poolId, String response) {
        ObjectId oId = getUserIdFromPoolId(poolId);
        Optional<User> u = userRepository.findById(oId);
        for(int i=0;i<u.get().enrolledGroups.size();i++){
            String grpId=u.get().enrolledGroups.get(i).id;
            Optional<Group> group = groupRepository.findById(new ObjectId(grpId));
            for(int j=0;j<group.get().users.size();j++){
                String userid=group.get().users.get(j).id;
                if(userid.equals(u.get().id)){
                    group.get().users.get(j).profileImage=u.get().profileImage;
                }
            }
            groupRepository.save(group.get());
        }
    }
}
