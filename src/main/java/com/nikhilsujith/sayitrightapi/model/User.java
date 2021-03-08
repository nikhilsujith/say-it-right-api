package com.nikhilsujith.sayitrightapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sayitright_user")
public class User {

    @Id
    private String id;
    private String poolId;
    private String fullName;
    private String profileImage;
    private String email;
    private String desc;
    private String audioFile;
    private String videoFile;
    @DBRef
    private ArrayList<Group> myGroups;
    @DBRef
    private ArrayList<Group> enrolledGroups;
    private String createdOn;


}



