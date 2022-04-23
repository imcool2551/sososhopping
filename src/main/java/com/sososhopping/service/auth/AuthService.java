package com.sososhopping.service.auth;

import com.sososhopping.common.dto.auth.request.AdminAuthRequestDto;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.entity.member.Admin;
import com.sososhopping.repository.member.AdminRepository;
import com.sososhopping.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    /**
     * 관리자 관련 인증
     */

    //관리자 회원가입
    @Transactional
    public void adminSignUp(AdminAuthRequestDto dto) {
        if(adminRepository.existsByNickname(dto.getNickname()))
            throw new Api400Exception("중복된 닉네임입니다");

        //계정 저장
        Admin admin = Admin.builder()
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        adminRepository.save(admin);
    }

    //관리자 로그인
    @Transactional
    public String adminLogin(AdminAuthRequestDto dto) {
        Admin admin = adminRepository.findByNickname(dto.getNickname()).orElseThrow(() ->
                new Api401Exception("올바르지 않은 닉네임입니다"));

        if (!passwordEncoder.matches(dto.getPassword(), admin.getPassword()))
            throw new Api401Exception("올바르지 않은 비밀번호입니다");

        return jwtTokenProvider.createToken("A", admin.getId());
    }

}
