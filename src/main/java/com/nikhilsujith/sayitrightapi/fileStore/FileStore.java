package com.nikhilsujith.sayitrightapi.fileStore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class FileStore {

    private final AmazonS3 s3;

    @Autowired
    public FileStore (AmazonS3 s3){
        this.s3 = s3;
    }

//    Save image
//    public void saveImageOLD(String path, String fileName, Optional<Map<String, String>> optionalMetadata,
//                          InputStream inputStream){
//
//        ObjectMetadata metadata = new ObjectMetadata();
//        optionalMetadata.ifPresent(map -> {
//            if (!map.isEmpty()) {
//                map.forEach(metadata :: addUserMetadata);
//            }
//        });
//
//        try{
//            s3.putObject(path, fileName, inputStream, metadata);
//        }
//        catch (AmazonServiceException e){
//            throw new IllegalStateException("Failed to store file to s3", e);
//        }
//    }
    public String saveImage(String path, String fileName, Optional<Map<String, String>> optionalMetadata,
                                MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata();
        File fileObject = convertMultipartToFile(file);
        optionalMetadata.ifPresent(map -> {
            if(!map.isEmpty()){
                map.forEach(metadata :: addUserMetadata);
            }
        });

        try{
//            AccessControlList acl = new AccessControlList();
//            s3.putObject(path, fileName, fileObject)
//            fileObject.delete();
//            return ("success");

            System.out.println(path+fileName);
            String key = path+"/"+fileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest("say-it-right-bucket", key ,fileObject);
            AccessControlList acl = new AccessControlList();
            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read); //all users or authenticated
            putObjectRequest.setAccessControlList(acl);
            putObjectRequest.setMetadata(metadata);
            s3.putObject(putObjectRequest);
            return "success";
        }
        catch (AmazonServiceException e){
            throw new IllegalStateException("Failed to store file to s3", e);
        }
    }

    //    Convert Multipart file to File
    private File convertMultipartToFile(MultipartFile file){
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try(FileOutputStream fileOutputStream = new FileOutputStream(convertedFile)){
            fileOutputStream.write(file.getBytes());
        }
        catch (IOException e){
            System.out.println("Error converting multipart file to file"+e);
        }
        return convertedFile;
    }


}
