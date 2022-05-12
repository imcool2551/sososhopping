package com.sososhopping.domain.auth.controller;

import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.auth.dto.request.AdminLoginDto;
import com.sososhopping.domain.auth.dto.response.LoginResponse;
import com.sososhopping.domain.auth.repository.AdminAuthRepository;
import com.sososhopping.entity.admin.Admin;
import com.sososhopping.common.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/admin/auth/login")
    public LoginResponse adminLogin(@ModelAttribute @Valid AdminLoginDto dto) {

        Admin admin = adminRepository.findByNickname(dto.getNickname())
                .orElseThrow(UnAuthorizedException::new);

        if (!passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            throw new UnAuthorizedException();
        }

        String token = jwtTokenProvider.createToken("A", admin.getId());
        return new LoginResponse(token);
    }

}
