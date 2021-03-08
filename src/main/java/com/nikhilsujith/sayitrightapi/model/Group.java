package com.nikhilsujith.sayitrightapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sayitright_group")
public class Group {

    @Id
    private String id;
    private String groupName;
    private String groupDesc;
    private String groupImage;
    private String groupCode;
    private JsonNode creatorId; //Object Id of creator
    private String creatorName; // Name of creator
    private String numberOfUsers;

    public Group() {
    }

    public Group(String id, String groupName, String groupDesc, String groupImage, String groupCode, JsonNode creatorId, String creatorName, String numberOfUsers) {
        this.id = id;
        this.groupName = groupName;
        this.groupDesc = groupDesc;
        this.groupImage = groupImage;
        this.groupCode = groupCode;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.numberOfUsers = numberOfUsers;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public JsonNode getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(JsonNode creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(String numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupDesc='" + groupDesc + '\'' +
                ", groupImage='" + groupImage + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", numberOfUsers='" + numberOfUsers + '\'' +
                '}';
    }
}
