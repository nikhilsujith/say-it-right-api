package com.nikhilsujith.sayitrightapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Group {

    private String groupId;
    private String groupName;
    private String groupImage;

}
