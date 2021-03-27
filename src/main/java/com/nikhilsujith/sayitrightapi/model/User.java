package com.nikhilsujith.sayitrightapi.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "sayitright_user")
@TypeAlias("User")
public class User {


    @Id
    public String id;
    public String poolId;
    public String fullName;
    public String profileImage; // S3 key
    public String email;
    public String desc;
    public String audioFile;
    public String videoFile;
    public List<UserGroup> myGroups;
    public List<UserGroup> enrolledGroups;
    public String createdOn;
    public String updatedOn;

//    TODO
//      https://stackoverflow.com/questions/26591307/how-to-save-an-object-with-null-dbref-in-mongodb-java-spring?rq=1

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(poolId, user.poolId) && Objects.equals(fullName, user.fullName) && Objects.equals(profileImage, user.profileImage) && Objects.equals(email, user.email) && Objects.equals(desc, user.desc) && Objects.equals(audioFile, user.audioFile) && Objects.equals(videoFile, user.videoFile) && Objects.equals(createdOn, user.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, poolId, fullName, profileImage, email, desc, audioFile, videoFile, createdOn);
    }
}



