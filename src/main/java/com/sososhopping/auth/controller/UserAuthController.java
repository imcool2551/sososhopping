package com.sososhopping.auth.controller;

import com.sososhopping.auth.dto.request.UserEmailCheckRequestDto;
import com.sososhopping.auth.dto.request.UserNicknameCheckRequestDto;
import com.sososhopping.auth.dto.request.UserPhoneCheckRequestDto;
import com.sososhopping.auth.dto.request.UserSignupRequestDto;
import com.sososhopping.auth.dto.response.LoginResponseDto;
import com.sososhopping.auth.repository.UserRepository;
import com.sososhopping.auth.service.UserAuthService;
import com.sososhopping.common.ApiResponse;
import com.sososhopping.auth.dto.request.UserLoginRequestDto;
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

    private final UserRepository userRepository;
    private final UserAuthService authService;

    @ResponseStatus(CREATED)
    @PostMapping("/users/auth/signup")
    public void userSignup(@RequestBody @Valid UserSignupRequestDto dto) {
        authService.userSignUp(dto);
    }

    @PostMapping("/users/auth/signup/validation")
    public ResponseEntity<ApiResponse> userCheckDuplicateEmail(
            @RequestBody @Valid UserEmailCheckRequestDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            return new ResponseEntity<>(new ApiResponse("email already in use"), CONFLICT);
        }

        return new ResponseEntity<>(new ApiResponse("email is ok to use"), OK);
    }

    @PostMapping("/users/auth/signup/nickname")
    public ResponseEntity<ApiResponse> userCheckDuplicateNickname(
            @RequestBody @Valid UserNicknameCheckRequestDto dto) {

        if (userRepository.existsByNickname(dto.getNickname())) {
            return new ResponseEntity<>(new ApiResponse("nickname already in use"), CONFLICT);
        }

        return new ResponseEntity<>(new ApiResponse("nickname is ok to use"), OK);
    }

    @PostMapping("/users/auth/signup/phone")
    public ResponseEntity<ApiResponse> userCheckDuplicatePhone(
            @RequestBody @Valid UserPhoneCheckRequestDto dto) {

        if (userRepository.existsByPhone(dto.getPhone())) {
            return new ResponseEntity<>(new ApiResponse("phone number already in use"), CONFLICT);
        }

        return new ResponseEntity<>(new ApiResponse("phone number is ok to use"), OK);
    }

    @PostMapping("/users/auth/login")
    public ResponseEntity<LoginResponseDto> userLogin(
            @RequestBody @Valid UserLoginRequestDto dto) {

        LoginResponseDto response = authService.userLogin(dto.getEmail(), dto.getPassword());
        return new ResponseEntity<>(response, OK);
    }
}
