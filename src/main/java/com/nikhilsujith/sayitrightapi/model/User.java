package com.nikhilsujith.sayitrightapi.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "sayitright_user")
@TypeAlias("User")
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
//    private List<Group> myGroups;
//    private List<Group> enrolledGroups;
    private String createdOn;

//    TODO
//      https://stackoverflow.com/questions/26591307/how-to-save-an-object-with-null-dbref-in-mongodb-java-spring?rq=1


}



