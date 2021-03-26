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
@TypeAlias("UserGroup")
public class UserGroup {

    public String id;
    public String groupName;
    public String groupImage;
}
