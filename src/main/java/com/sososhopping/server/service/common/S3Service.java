package com.sososhopping.server.service.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sososhopping.server.common.error.Api500Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file, String dirPath) throws IOException {
        String fileName = createFileName(file.getOriginalFilename());
        String uploadPath = dirPath + "/" + fileName;

        s3Client.putObject(
                new PutObjectRequest(bucket, uploadPath, file.getInputStream(), null)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        return s3Client.getUrl(bucket, uploadPath).toString();
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getExtension(fileName));
    }

    private String getExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new Api500Exception("파일 형식이 올바르지 않습니다.");
        }
    }

//    public void delete(String filePath) {
//        s3Client.deleteObject(
//                new DeleteObjectRequest(bucket, bucket+"/"+filePath));
//    }
}
