package com.sososhopping.domain.auth.controller;

import com.sososhopping.common.ApiResponse;
import com.sososhopping.domain.auth.dto.request.OwnerEmailCheckDto;
import com.sososhopping.domain.auth.repository.OwnerAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OwnerAuthController {

    private final OwnerAuthRepository ownerRepository;

    @PostMapping("/owner/auth/signup/validation")
    public ResponseEntity<ApiResponse> ownerCheckDuplicateEmail(
            @RequestBody @Valid OwnerEmailCheckDto dto) {

        if (ownerRepository.existsByEmail(dto.getEmail())) {
            return new ResponseEntity<>(new ApiResponse("email already in use"), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(new ApiResponse("email is ok to use"), HttpStatus.OK);
    }
}
