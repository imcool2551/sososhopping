package com.sososhopping.domain.auth.controller;

import com.sososhopping.common.ApiResponse;
import com.sososhopping.domain.auth.dto.request.DuplicateEmailCheckDto;
import com.sososhopping.domain.auth.dto.request.UserLoginDto;
import com.sososhopping.domain.auth.dto.request.DuplicatePhoneCheckDto;
import com.sososhopping.domain.auth.dto.request.UserSignupDto;
import com.sososhopping.domain.auth.dto.response.LoginResponse;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.domain.auth.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthRepository userRepository;
    private final UserAuthService authService;

    @PostMapping("/users/auth/signup/validation/email")
    public ResponseEntity<ApiResponse> userCheckDuplicateEmail(
            @RequestBody @Valid DuplicateEmailCheckDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            return new ResponseEntity<>(new ApiResponse("email already in use"), CONFLICT);
        }

        return new ResponseEntity<>(new ApiResponse("email is ok to use"), OK);
    }

    @PostMapping("/users/auth/signup/validation/phone")
    public ResponseEntity<ApiResponse> userCheckDuplicatePhone(
            @RequestBody @Valid DuplicatePhoneCheckDto dto) {

        if (userRepository.existsByPhone(dto.getPhone())) {
            return new ResponseEntity<>(new ApiResponse("phone number already in use"), CONFLICT);
        }

        return new ResponseEntity<>(new ApiResponse("phone number is ok to use"), OK);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/users/auth/signup")
    public void userSignup(@RequestBody @Valid UserSignupDto dto) {
        authService.signup(dto);
    }

    @PostMapping("/users/auth/login")
    public LoginResponse userLogin(@RequestBody @Valid UserLoginDto dto) {
        return authService.login(dto.getEmail(), dto.getPassword());
    }
}
