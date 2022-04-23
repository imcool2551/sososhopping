package com.sososhopping.domain.auth.service;

import com.sososhopping.domain.auth.dto.request.OwnerSignUpDto;
import com.sososhopping.domain.auth.repository.OwnerAuthRepository;
import com.sososhopping.entity.member.AccountStatus;
import com.sososhopping.entity.member.Owner;
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

    public void ownerSignup(OwnerSignUpDto dto) {

        Owner owner = Owner.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .phone(dto.getPhone())
                .active(AccountStatus.ACTIVE)
                .build();

        ownerRepository.save(owner);
    }
}
