package com.nikhilsujith.sayitrightapi.service;

import com.nikhilsujith.sayitrightapi.bucket.BucketName;
import com.nikhilsujith.sayitrightapi.fileStore.FileStore;
import com.nikhilsujith.sayitrightapi.model.User;
import com.nikhilsujith.sayitrightapi.repository.GroupRepository;
import com.nikhilsujith.sayitrightapi.repository.UserRepository;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class S3Service {

//    User Service
    @Autowired
    UserService userService;

//    User Repository
    @Autowired
    UserRepository userRepository;

//    Group Repository
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    FileStore fileStore;

    public String uploadImage(String poolId, MultipartFile file) {
        String response;
        isFileEmpty(file);
        isImage(file);
        response = uploadToS3(poolId, file);
        updateImageLink(poolId, response);
        return response;
    }



    private void updateImageLink(String poolId, String response) {
        ObjectId oId = userService.getUserIdFromPoolId(poolId);
        Optional<User> user = userRepository.findById(oId);
        User updatedUser = user.get();
        updatedUser.setProfileImage(response);
        userRepository.save(updatedUser);
    }

    @NotNull
    private String uploadToS3(String id, MultipartFile file) {
        String response;
        Map<String, String> metadata = extractMetada(file);

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), id);
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
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

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofBytes(100000000L));
        factory.setMaxRequestSize(DataSize.ofBytes(100000000L));
        return factory.createMultipartConfig();
    }

}
