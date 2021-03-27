package com.nikhilsujith.sayitrightapi.service;

import com.nikhilsujith.sayitrightapi.bucket.BucketName;
import com.nikhilsujith.sayitrightapi.fileStore.FileStore;
import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.model.User;
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

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserService {

//    Get Repository
    @Autowired
    UserRepository userRepository;
    //    Get S3 File Store
    @Autowired
    FileStore fileStore;

//  Get repository
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    S3Service s3Service;

    //    Get all user data
    public List<User> getAllUserData() {
        return userRepository.findAll();
    }

    //    Get user by UserId
    public Optional<User> getUserById(ObjectId userId) {
        return userRepository.findById(userId);
    }

    /*--------------------------POST--------------------------------------------*/
    public void addNewUser(User user) {
        userRepository.save(user);
    }

//    update database with image link
    public String updateDatabaseImageLink(String link){
        return "updated";
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
