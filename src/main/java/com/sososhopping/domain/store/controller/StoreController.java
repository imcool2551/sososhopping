package com.sososhopping.domain.store.controller;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.domain.store.dto.request.CreateStoreDto;
import com.sososhopping.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/owner/my/store/upload")
    public ResponseEntity<ApiResponse> upload(
            Authentication authentication,
            @RequestParam MultipartFile file) throws IOException {

        Long ownerId = Long.parseLong(authentication.getName());
        String imgUrl = storeService.upload(ownerId, file);
        return new ResponseEntity<>(new ApiResponse(imgUrl), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/owner/my/store")
    public void createStore(Authentication authentication, @RequestBody @Valid CreateStoreDto dto) {

    }
}
