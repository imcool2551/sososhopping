package com.sososhopping.domain.store.controller;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping(value = "/owner/my/store/image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse> uploadImage(
            Authentication authentication,
            @RequestPart MultipartFile file) throws IOException {

        Long ownerId = Long.parseLong(authentication.getName());
        String imgUrl = storeService.uploadImage(ownerId, file);
        return new ResponseEntity<>(new ApiResponse(imgUrl), HttpStatus.OK);
    }
}
