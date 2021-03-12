package com.nikhilsujith.sayitrightapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "groups")
@TypeAlias("Group")
public class Group {

    @Id
    private UUID _id;

    private String groupName;
    private String groupDesc;
    private String groupImage;
    private String creatorId; //Object Id of creator
    private String creatorName; // Name of creator
    private String numberOfUsers;

    public Group(UUID _id, String groupName, String groupDesc, String groupImage, String creatorId, String creatorName) {
        this._id = UUID.randomUUID();
        this.groupName = groupName;
        this.groupDesc = groupDesc;
        this.groupImage = groupImage;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
    }
}

//TODO
//    Create user
//      Create group