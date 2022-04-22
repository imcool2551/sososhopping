package com.sososhopping.domain.auth.controller;

import com.sososhopping.common.ApiResponse;
import com.sososhopping.domain.auth.dto.request.UserEmailCheckDto;
import com.sososhopping.domain.auth.dto.request.UserLoginDto;
import com.sososhopping.domain.auth.dto.request.UserPhoneCheckDto;
import com.sososhopping.domain.auth.dto.request.UserSignupDto;
import com.sososhopping.domain.auth.dto.response.LoginResponse;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.domain.auth.service.UserAuthService;
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
public class UserAuthController {

    private final UserAuthRepository userRepository;
    private final UserAuthService authService;

    @PostMapping("/users/auth/signup/validation")
    public ResponseEntity<ApiResponse> userCheckDuplicateEmail(
            @RequestBody @Valid UserEmailCheckDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            return new ResponseEntity<>(new ApiResponse("email already in use"), CONFLICT);
        }

        return new ResponseEntity<>(new ApiResponse("email is ok to use"), OK);
    }

    @PostMapping("/users/auth/signup/phone")
    public ResponseEntity<ApiResponse> userCheckDuplicatePhone(
            @RequestBody @Valid UserPhoneCheckDto dto) {

        if (userRepository.existsByPhone(dto.getPhone())) {
            return new ResponseEntity<>(new ApiResponse("phone number already in use"), CONFLICT);
        }

        return new ResponseEntity<>(new ApiResponse("phone number is ok to use"), OK);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/users/auth/signup")
    public void userSignup(@RequestBody @Valid UserSignupDto dto) {
        authService.userSignUp(dto);
    }

    @PostMapping("/users/auth/login")
    public ResponseEntity<LoginResponse> userLogin(
            @RequestBody @Valid UserLoginDto dto) {

        LoginResponse response = authService.userLogin(dto.getEmail(), dto.getPassword());
        return new ResponseEntity<>(response, OK);
    }
}
