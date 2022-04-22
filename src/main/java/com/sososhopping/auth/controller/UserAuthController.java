package com.sososhopping.auth.controller;

import com.sososhopping.auth.dto.request.UserSignUpRequestDto;
import com.sososhopping.auth.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService authService;

    @ResponseStatus(CREATED)
    @PostMapping("/users/auth/signup")
    public void userSignup(@RequestBody @Valid UserSignUpRequestDto dto) {
        authService.userSignUp(dto);
    }

}
