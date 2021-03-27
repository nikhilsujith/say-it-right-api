package com.nikhilsujith.sayitrightapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
//@Document(collection = "groups")
@Document(collection = "sayitright_group")
@TypeAlias("Group")
public class Group {

    @Id
    public String id;

    public String groupName;
    public String groupDesc;
    public String groupImage;
    public String creatorId; //Object Id of creator
    public String creatorName; // Name of creator
    public String createrPoolId;
    public List<GroupMember> users;
//
//    public Group(UUID _id, String groupName, String groupDesc, String groupImage, String creatorId, String creatorName) {
//        this._id = UUID.randomUUID();
//        this.groupName = groupName;
//        this.groupDesc = groupDesc;
//        this.groupImage = groupImage;
//        this.creatorId = creatorId;
//        this.creatorName = creatorName;
//    }
	
	
}

//TODO
//    Create user
//      Create group