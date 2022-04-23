package com.sososhopping.domain.auth.service;

import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.auth.dto.request.OwnerSignUpDto;
import com.sososhopping.domain.auth.dto.response.LoginResponse;
import com.sososhopping.domain.auth.exception.InvalidCredentialsException;
import com.sososhopping.domain.auth.repository.OwnerAuthRepository;
import com.sososhopping.entity.member.AccountStatus;
import com.sososhopping.entity.member.Owner;
import com.sososhopping.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerAuthService {

    private final OwnerAuthRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(OwnerSignUpDto dto) {

        Owner owner = Owner.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .phone(dto.getPhone())
                .active(AccountStatus.ACTIVE)
                .build();

        ownerRepository.save(owner);
    }

    public LoginResponse login(String email, String password) {

        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(password, owner.getPassword())) {
            throw new InvalidCredentialsException();
        }

        if (!owner.isActive()) {
            throw new UnAuthorizedException("account is not active");
        }

        String token = jwtTokenProvider.createToken("O", owner.getId());
        return new LoginResponse(token);
    }
}
