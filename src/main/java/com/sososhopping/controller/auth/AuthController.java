package com.sososhopping.controller.auth;

import com.sososhopping.common.dto.auth.request.AdminAuthRequestDto;
import com.sososhopping.common.dto.auth.response.LoginResponseDto;
import com.sososhopping.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

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
    @PostMapping("/api/v1/admin/auth/login")
    public ResponseEntity adminLogin(@ModelAttribute AdminAuthRequestDto dto) {
        String token = authService.adminLogin(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginResponseDto(token, null));
    }
}
