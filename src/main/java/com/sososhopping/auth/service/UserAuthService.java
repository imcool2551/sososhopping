package com.sososhopping.auth.service;

import com.sososhopping.auth.dto.request.UserSignupRequestDto;
import com.sososhopping.auth.dto.response.LoginResponseDto;
import com.sososhopping.auth.exception.DuplicateMemberException;
import com.sososhopping.auth.exception.InvalidCredentialsException;
import com.sososhopping.auth.exception.NotAuthorizedException;
import com.sososhopping.auth.repository.UserRepository;
import com.sososhopping.entity.member.AccountStatus;
import com.sososhopping.entity.user.User;
import com.sososhopping.security.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public void userSignUp(UserSignupRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateMemberException("email already in use");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .phone(dto.getPhone())
                .nickname(dto.getNickname())
                .streetAddress(dto.getStreet())
                .detailedAddress(dto.getDetail())
                .active(AccountStatus.ACTIVE)
                .build();

        userRepository.save(user);
    }

    public LoginResponseDto userLogin(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("wrong credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("wrong credentials");
        }

        if (!user.isActive()) {
            throw new NotAuthorizedException("account is not active");
        }

        String token = jwtTokenProvider.createToken("U", user.getId());
        return new LoginResponseDto(token);
    }
}
