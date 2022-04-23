package com.sososhopping.domain.auth.service;

import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.auth.dto.request.UserSignupDto;
import com.sososhopping.domain.auth.dto.response.LoginResponse;
import com.sososhopping.domain.auth.exception.InvalidCredentialsException;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.entity.member.AccountStatus;
import com.sososhopping.entity.user.User;
import com.sososhopping.common.security.JwtTokenProvider;
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

    private final UserAuthRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public void signup(UserSignupDto dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .nickname(dto.getNickname())
                .phone(dto.getPhone())
                .streetAddress(dto.getStreet())
                .detailedAddress(dto.getDetail())
                .active(AccountStatus.ACTIVE)
                .build();

        userRepository.save(user);
    }

    public LoginResponse login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        if (!user.isActive()) {
            throw new UnAuthorizedException("account is not active");
        }

        String token = jwtTokenProvider.createToken("U", user.getId());
        return new LoginResponse(token);
    }
}
