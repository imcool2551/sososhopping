package com.sososhopping.server.service.user.info;

import com.sososhopping.server.common.dto.user.request.info.UserInfoUpdateDto;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.common.error.Api409Exception;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.repository.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User updateUserInfo(Long userId, UserInfoUpdateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

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
