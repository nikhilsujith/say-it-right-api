package com.nikhilsujith.sayitrightapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sayitright_group")
public class Group {

    @Id
    private String _id;
    private String groupName;
    private String groupDesc;
    private String groupImage;
    private String groupCode;
    private String creatorId; //Object Id of creator
    private String creatorName; // Name of creator
    private String numberOfUsers;


}
