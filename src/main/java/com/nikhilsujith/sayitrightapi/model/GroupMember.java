package com.nikhilsujith.sayitrightapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

@Data
@NoArgsConstructor
@Getter
@Setter
@TypeAlias("UserGroup")
public class GroupMember {

    public String id;
    public String poolId;
    public String fullName;
    public String profileImage;
}
