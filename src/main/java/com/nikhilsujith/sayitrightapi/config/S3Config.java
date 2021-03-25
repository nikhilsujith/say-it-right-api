package com.nikhilsujith.sayitrightapi.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${amazon.s3.endpoint}")
    private String amazonS3Endpoint;

    @Value("${amazon.s3.bucketName}")
    private String amazonS3BucketName;

    @Value("${amazon.aws.region}")
    private String amazonAWSRegion;

    @Value("${amazon.aws.profile}")
    private String amazonAWSProfile;

    public S3Config() {
    }


//    OLD -----------------------------------------------------------
//    @Bean
//    public AmazonDynamoDB amazonDynamoDB() {
//        AmazonDynamoDBClientBuilder builder = configDBClientBuilder();
//        return builder.build();
//    }

//    @Bean
//    AmazonDynamoDBClientBuilder configDBClientBuilder() {
//
//        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
//        builder.setEndpointConfiguration(amazonEndpointConfiguration());
//        builder.withCredentials(amazonAWSCredentials());
//        return builder;
//    }

    //    @Bean
//    public AmazonDynamoDB amazonDynamoDBConfig(){
//        return AmazonDynamoDBClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonS3Endpoint, amazonAWSRegion))
//                .withCredentials(amazonAWSCredentials()).build();
//    }

//    OLD -----------------------------------------------------------



//    NEW -----------------------------------------------------------

//    @Bean
//    public AmazonS3 amazonS3() {
//        AmazonS3ClientBuilder builder = configS3ClientBuilder();
//        return builder.build();
//    }
//
//    @Bean
//    AmazonS3ClientBuilder configS3ClientBuilder() {
//        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
//        builder.setEndpointConfiguration(amazonEndpointConfiguration());
//        builder.withCredentials(amazonAWSCredentials());
//        return builder;
//    }
//
//    private AwsClientBuilder.EndpointConfiguration amazonEndpointConfiguration() {
//        return new AwsClientBuilder.EndpointConfiguration(amazonS3Endpoint, amazonAWSRegion);
//    }
//
//    private AWSCredentialsProvider amazonAWSCredentials() {
//        return new ProfileCredentialsProvider(amazonAWSProfile);
//    }

//    NEW -----------------------------------------------------------

    @Bean
    public AmazonS3 s3(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                "AKIA5CWBRTH4K23KGCGI",
                "2IdoeIKpWk2ghtyY9ycHI3SVPFQH4dlFIJRTm+O4"
                //                "AKIAXUBPRCRLVTF7EOHW",
                //                "FbCqtSgpvdzA5GCppnk9BbyUhc7EmHrnm7+ChckA"
        );
//        Following the builder pattern
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        "https://s3.us-east-1.amazonaws.com","us-east-1"))
                .build();
    }
}

