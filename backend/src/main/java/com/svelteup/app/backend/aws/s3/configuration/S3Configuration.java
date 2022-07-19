package com.svelteup.app.backend.aws.s3.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
public class S3Configuration {

    @Bean()
    public BasicAWSCredentials getAwsCredentials(Environment springEnvironment)
    {
        String accessKey = springEnvironment.getProperty("cloud.aws.credentials.access-key");
        String secretKey = springEnvironment.getProperty("cloud.aws.credentials.secret-key");
        return new BasicAWSCredentials(accessKey,secretKey);
    }
    @Bean()
    @Primary()
    public AmazonS3 getS3Client(BasicAWSCredentials awsCredentials)
    {
        AmazonS3 amazonS3Client = AmazonS3Client
                .builder()
                .withCredentials( new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.US_WEST_1)
                .build();

        return amazonS3Client;
    }

    @Bean()
    public TransferManager getTransferManager(AmazonS3 s3Client)
    {
        return TransferManagerBuilder
                .standard()
                .withS3Client(s3Client)
                .withMultipartUploadThreshold(5000000L)
                .build();
    }
}
