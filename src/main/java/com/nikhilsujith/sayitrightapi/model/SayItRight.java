package com.nikhilsujith.sayitrightapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "sayitright_collection")
public class SayItRight {
    @Id
    private String id;
    private String user_name;
    private String user_email;
    private String user_full_name;
    private String description;
    private String profile_image_path;

    public SayItRight(String id,
                      String user_name,
                      String user_email,
                      String user_full_name,
                      String description,
                      String profile_image_path) {
        this.id = id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_full_name = user_full_name;
        this.description = description;
        this.profile_image_path = profile_image_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public void setUser_full_name(String user_full_name) {
        this.user_full_name = user_full_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfile_image_path() {
        return profile_image_path;
    }

    public void setProfile_image_path(String profile_image_path) {
        this.profile_image_path = profile_image_path;
    }
}