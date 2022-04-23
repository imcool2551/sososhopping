package com.sososhopping.domain.auth.controller;

import com.sososhopping.common.ApiResponse;
import com.sososhopping.domain.auth.dto.request.DuplicateEmailCheckDto;
import com.sososhopping.domain.auth.dto.request.DuplicatePhoneCheckDto;
import com.sososhopping.domain.auth.dto.request.OwnerSignUpDto;
import com.sososhopping.domain.auth.repository.OwnerAuthRepository;
import com.sososhopping.domain.auth.service.OwnerAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OwnerAuthController {

    private final OwnerAuthService ownerAuthService;
    private final OwnerAuthRepository ownerRepository;

    @PostMapping("/owner/auth/signup/validation/email")
    public ResponseEntity<ApiResponse> ownerCheckDuplicateEmail(
            @RequestBody @Valid DuplicateEmailCheckDto dto) {

        if (ownerRepository.existsByEmail(dto.getEmail())) {
            return new ResponseEntity<>(new ApiResponse("email already in use"), CONFLICT);
        }

        return new ResponseEntity<>(new ApiResponse("email is ok to use"), OK);
    }

    @PostMapping("/owner/auth/signup/validation/phone")
    public ResponseEntity<ApiResponse> ownerCheckDuplicatePhone(
            @RequestBody @Valid DuplicatePhoneCheckDto dto) {

        if (ownerRepository.existsByPhone(dto.getPhone())) {
            return new ResponseEntity<>(new ApiResponse("phone already in use"), CONFLICT);
        }

        return new ResponseEntity<>(new ApiResponse("phone is ok to use"), OK);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/owner/auth/signup")
    public void ownerSignup(@RequestBody @Valid OwnerSignUpDto dto) {
        ownerAuthService.ownerSignup(dto);
    }
}
