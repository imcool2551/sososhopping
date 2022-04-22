package com.sososhopping.auth.controller;

import com.sososhopping.auth.dto.request.UserEmailCheckRequestDto;
import com.sososhopping.auth.dto.request.UserSignupRequestDto;
import com.sososhopping.auth.repository.UserRepository;
import com.sososhopping.auth.service.UserAuthService;
import com.sososhopping.common.ApiResponse;
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
    public ResponseEntity<ApiResponse> userSignUpValidation(
            @RequestBody @Valid UserEmailCheckRequestDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            return new ResponseEntity(new ApiResponse("email already in use"), CONFLICT);
        }

        return new ResponseEntity(new ApiResponse("email is ok to use"), OK);
    }

}
