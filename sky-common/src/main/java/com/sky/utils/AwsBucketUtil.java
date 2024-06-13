package com.sky.utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.Upload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Data
@Slf4j
@AllArgsConstructor

public class AwsBucketUtil{
    private String region;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;


    /**
     * 创建aws客户端实例 上传文件
     */
    public String upload(byte[] bytes,String objectName){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, accessKeySecret);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        log.info("Amazon S3 Client initialized successfully");

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);
            metadata.setContentType("image/jpeg");
            String uuid = UUID.randomUUID().toString();
            String extension = objectName.substring(objectName.lastIndexOf('.'));
            objectName = "pic/"+uuid+extension;
            // 上传文件
            s3Client.putObject(bucketName, objectName, byteArrayInputStream, metadata);

            // 构造文件访问 URL
            URL s3Url = s3Client.getUrl(bucketName, objectName);
            log.info("File uploaded successfully. File URL: {}", s3Url.toString());
            return s3Url.toString();
        } catch (Exception e) {
            log.error("Error uploading file to S3", e);
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }
}