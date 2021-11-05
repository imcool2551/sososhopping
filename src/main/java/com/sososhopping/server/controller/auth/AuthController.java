package com.sososhopping.server.controller.auth;

import com.sososhopping.server.common.dto.auth.request.*;
import com.sososhopping.server.common.dto.auth.response.LoginResponseDto;
import com.sososhopping.server.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 점주 관련 인증
     */
    //점주 이메일 중복 확인
    @PostMapping(value = "/api/v1/owner/auth/signup/validation")
    public ResponseEntity ownerSignUpValidation(@RequestBody OwnerSignUpRequestDto dto) {
        if(authService.ownerSignUpValidation(dto.getEmail()))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.CONFLICT);
    }

    //점주 회원가입
    @PostMapping(value = "/api/v1/owner/auth/signup")
    public ResponseEntity ownerSignUp(@RequestBody OwnerSignUpRequestDto dto) {
        authService.ownerSignUp(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //점주 로그인
    @PostMapping(value = "/api/v1/owner/auth/login")
    public ResponseEntity ownerLogin(@RequestBody OwnerLoginRequestDto dto) {
        String token = authService.ownerLogin(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginResponseDto(token));
    }

    /**
     * 고객 관련 인증
     */
    //고객 이메일 중복 확인
    @PostMapping(value = "/api/v1/users/auth/signup/validation")
    public ResponseEntity userSignUpValidation(@RequestBody UserSignUpRequestDto dto) {
        if(authService.userSignUpValidation(dto.getEmail()))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.CONFLICT);
    }


    //고객 회원가입
    @PostMapping(value = "/api/v1/users/auth/signup")
    public ResponseEntity userSignUp(@RequestBody UserSignUpRequestDto dto) {
        authService.userSignUp(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //고객 로그인
    @PostMapping(value = "/api/v1/users/auth/login")
    public ResponseEntity userLogin(@RequestBody UserLoginRequestDto dto) {
        String token = authService.userLogin(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginResponseDto(token));
    }

    /**
     * 관리자 관련 인증
     */
    //관리자 회원가입
    @PostMapping(value = "/api/v1/admin/auth/signup")
    public ResponseEntity adminSignUp(@RequestBody AdminAuthRequestDto dto) {
        authService.adminSignUp(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //관리자 로그인
    @PostMapping(value = "/api/v1/admin/auth/login")
    public ResponseEntity adminLogin(@RequestBody AdminAuthRequestDto dto) {
        String token = authService.adminLogin(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginResponseDto(token));
    }
}
