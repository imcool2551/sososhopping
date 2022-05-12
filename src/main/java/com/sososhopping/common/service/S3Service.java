package com.sososhopping.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(String dirPath, MultipartFile file) throws IOException {
        String fileName = createFileName(file.getOriginalFilename());
        String uploadPath = dirPath + "/" + fileName;

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucket, uploadPath, file.getInputStream(), null);

        s3Client.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
        return uploadPath;
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID()
                .toString()
                .concat(getExtension(fileName));
    }

    private String getExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("파일 형식이 올바르지 않습니다.");
        }
    }

}
