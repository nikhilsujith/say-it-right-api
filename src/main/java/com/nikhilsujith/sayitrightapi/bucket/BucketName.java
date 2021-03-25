package com.nikhilsujith.sayitrightapi.bucket;

public enum BucketName {

    //    PROFILE_IMAGE("react-spring-aws--image-upload-download-full-stack");
    PROFILE_IMAGE("say-it-right-bucket");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}

