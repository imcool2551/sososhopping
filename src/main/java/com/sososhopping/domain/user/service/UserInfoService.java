package com.sososhopping.domain.user.service;

import com.sososhopping.domain.user.dto.request.UserInfoUpdateDto;
import com.sososhopping.common.error.Api409Exception;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserAuthRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User updateUserInfo(Long userId, UserInfoUpdateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        String phone = dto.getPhone();
        String password = dto.getPassword();

        userRepository.findByPhone(phone)
                .ifPresent(
                        existingUser -> {
                            if (user != existingUser)
                                throw new Api409Exception("이미 존재하는 번호입니다");
                        }
                );

        user.updateUserInfo(dto.getName(), phone, dto.getNickname(), dto.getStreetAddress(), dto.getDetailedAddress());

        if (password != null) {
            String encodedPassword = passwordEncoder.encode(password);
            user.updatePassword(encodedPassword);
        }

        return user;
    }
}
