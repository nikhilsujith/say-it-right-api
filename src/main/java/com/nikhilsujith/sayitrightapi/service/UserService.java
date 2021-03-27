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

//        Get repository
    @Autowired
    GroupRepository repo;

    //  User
    User user;

    @Autowired
    GroupRepository groupRepository;

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


//        repository.save(group);
//        TODO
//            Need to save group reference
//                Help At:
//            https://stackoverflow.com/questions/51943411/cannot-create-a-reference-to-an-object-with-a-null-id-mongo-hibernate-mongo-and
//              https://stackoverflow.com/questions/43757776/save-document-with-dbref-in-mongodb-spring-data
    }

//    update database with image link
    public String updateDatabaseImageLink(String link){
        return "updated";
    }


    /*------------------------Image---------------------------*/


    public String uploadImage(String id, MultipartFile file) {
        String response;
        isFileEmpty(file);
        isImage(file);
        response = uploadToS3(id, file);
        updateImageLink(id, response);
        return response;
    }

    private void updateImageLink(String id, String response) {
        //        Update Database
        ObjectId oId = new ObjectId(id);
        Optional<User> user = userRepository.findById(oId);
//        TODO
//          Handle null case
        User updatedUser = user.get();
        updatedUser.setProfileImage(response);
        userRepository.save(updatedUser);
    }

    @NotNull
    private String uploadToS3(String id, MultipartFile file) {
        String response;
        Map<String, String> metadata = extractMetada(file);

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), id);
        String fileName = String.format("%s-%s", file.getName(), UUID.randomUUID());
        String url = "https://say-it-right-bucket.s3.amazonaws.com" + "/" + path + "/" + fileName;

        try {
            fileStore.saveImage(path, fileName, Optional.of(metadata), file.getInputStream());
            response = url;
        } catch (IOException e) {
            response = "Image upload failed";
            throw new IllegalStateException(e);
        }
        return response;
    }

    @NotNull
    private Map<String, String> extractMetada(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be of type jpeg, png or gif");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [" + file.getSize() + "]");
        }
    }

//    public byte[] downloadUserProfileImage(String id) {
////        User user = getUserOrThrow
//    }
}
