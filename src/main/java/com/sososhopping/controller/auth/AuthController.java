package com.sososhopping.controller.auth;

import com.sososhopping.common.dto.AuthToken;
import com.sososhopping.common.dto.auth.request.AdminAuthRequestDto;
import com.sososhopping.common.dto.auth.request.OwnerLoginRequestDto;
import com.sososhopping.common.dto.auth.request.OwnerSignUpRequestDto;
import com.sososhopping.common.dto.auth.request.OwnerUpdateInfoRequestDto;
import com.sososhopping.common.dto.auth.response.LoginResponseDto;
import com.sososhopping.common.dto.auth.response.OwnerInfoResponseDto;
import com.sososhopping.domain.auth.repository.OwnerAuthRepository;
import com.sososhopping.entity.member.Owner;
import com.sososhopping.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final OwnerAuthRepository ownerRepository;

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
        AuthToken authToken = authService.ownerLogin(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginResponseDto(authToken.getApiToken(), authToken.getFirebaseToken()));
    }

    @GetMapping("/api/v1/owner/auth/info")
    public OwnerInfoResponseDto ownerInfo(
            Authentication authentication
    ) {
        Long ownerId = Long.parseLong(authentication.getName());
        Owner owner = ownerRepository.findById(ownerId).get();
        return new OwnerInfoResponseDto(owner);
    }

    @PatchMapping("/api/v1/owner/auth/info")
    public void updateOwnerInfo(
            Authentication authentication,
            @RequestBody @Valid OwnerUpdateInfoRequestDto dto
    ) {
        Long ownerId = Long.parseLong(authentication.getName());
        authService.updateOwnerInfo(ownerId, dto);
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
    @PostMapping("/api/v1/admin/auth/login")
    public ResponseEntity adminLogin(@ModelAttribute AdminAuthRequestDto dto) {
        String token = authService.adminLogin(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginResponseDto(token, null));
    }
}
