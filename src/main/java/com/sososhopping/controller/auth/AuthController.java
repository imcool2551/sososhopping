package com.sososhopping.controller.auth;

import com.sososhopping.auth.dto.request.UserSignUpRequestDto;
import com.sososhopping.common.dto.AuthToken;
import com.sososhopping.common.dto.ErrorResponse;
import com.sososhopping.common.dto.auth.request.*;
import com.sososhopping.common.dto.auth.response.LoginResponseDto;
import com.sososhopping.common.dto.auth.response.OwnerFindEmailResponseDto;
import com.sososhopping.common.dto.auth.response.OwnerInfoResponseDto;
import com.sososhopping.entity.member.Owner;
import com.sososhopping.repository.member.OwnerRepository;
import com.sososhopping.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.sql.SQLException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
// @RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final OwnerRepository ownerRepository;

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> DataAccessException(SQLException e) {
        log.error("DataAccessException", e);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("database error"));
    }

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

    // 점주 이메일 찾기
    @PostMapping("/api/v1/owner/auth/findEmail")
    public OwnerFindEmailResponseDto findOwnerEmail(@RequestBody @Valid OwnerFindEmailRequestDto dto) {
        return authService.findOwnerEmail(dto);
    }

    // 점주 비밀번호 찾기
    @PostMapping("/api/v1/owner/auth/findPassword")
    public void findOwnerPassword(@RequestBody @Valid OwnerFindPasswordRequestDto dto) {
        authService.findOwnerPassword(dto);
    }

    // 점주 비밀번호 변경
    @PostMapping("/api/v1/owner/auth/changePassword")
    public void changeOwnerPassword(@RequestBody @Valid OwnerChangePasswordRequestDto dto) {
        authService.changeOwnerPassword(dto);
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

    @PatchMapping("/api/v1/owner/auth/info/password")
    public void updateOwnerPassword(
            Authentication authentication,
            @RequestBody @Valid OwnerUpdatePasswordRequest dto
    ) {
        Long ownerId = Long.parseLong(authentication.getName());
        authService.updateOwnerPassword(ownerId, dto);
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

    //고객 닉네임 중복 확인
    @PostMapping(value = "/api/v1/users/auth/signup/nickname")
    public ResponseEntity isDuplicateNickname(@RequestBody UserSignUpRequestDto dto) {
        if(authService.isDuplicateNickname(dto.getNickname()))
            return new ResponseEntity(HttpStatus.CONFLICT);
        else
            return new ResponseEntity(HttpStatus.OK);
    }

    //고객 핸드폰 중복 확인
    @PostMapping(value = "/api/v1/users/auth/signup/phone")
    public ResponseEntity isDuplicatePhone(@RequestBody UserSignUpRequestDto dto) {
        if(authService.isDuplicatePhone(dto.getPhone()))
            return new ResponseEntity(HttpStatus.CONFLICT);
        else
            return new ResponseEntity(HttpStatus.OK);
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
        AuthToken authToken = authService.userLogin(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginResponseDto(authToken.getApiToken(), authToken.getFirebaseToken()));
    }

    // 고객 이메일 찾기
    @PostMapping("/api/v1/users/auth/findEmail")
    public String findUserEmail(@RequestBody @Valid UserFindEmailDto dto) {
        return authService.findUserEmail(dto);
    }

    // 고객 비밀번호 찾기
    @PostMapping("/api/v1/users/auth/findPassword")
    public void findUserPassword(@RequestBody @Valid UserFindPasswordDto dto) {
        authService.findUserPassword(dto);
    }

    // 고객 비밀번호 변경
    @PostMapping("/api/v1/users/auth/changePassword")
    public void changeUserPassword(@RequestBody @Valid UserChangePasswordDto dto) {
        authService.changeUserPassword(dto);
    }

    // 고객 회원탈퇴
    @DeleteMapping("/api/v1/users/quit")
    public void deleteUser(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        authService.deleteUser(userId);
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
