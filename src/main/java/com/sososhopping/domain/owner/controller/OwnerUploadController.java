package com.sososhopping.domain.owner.controller;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.domain.owner.service.OwnerUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OwnerUploadController {

    private final OwnerUploadService ownerUploadService;

    @PostMapping("/owner/upload")
    public ResponseEntity<ApiResponse> upload(Authentication authentication,
                                              @RequestParam MultipartFile file) throws IOException {

        Long ownerId = Long.parseLong(authentication.getName());
        String imgUrl = ownerUploadService.upload(ownerId, file);
        return new ResponseEntity<>(new ApiResponse(imgUrl), CREATED);
    }
}
