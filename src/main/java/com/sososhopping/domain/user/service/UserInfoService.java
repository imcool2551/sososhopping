package com.sososhopping.domain.user.service;

import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.user.dto.request.UserInfoUpdateDto;
import com.sososhopping.domain.user.exception.DuplicatePhoneException;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserRepository userRepository;

    @Transactional
    public Long updateUserInfo(Long userId, UserInfoUpdateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        userRepository.findByPhone(dto.getPhone())
                .ifPresent(existingUser -> {
                            if (user != existingUser) {
                                throw new DuplicatePhoneException();
                            }
                });

        user.updateUserInfo(dto.getName(), dto.getPhone(), dto.getNickname(), dto.getStreetAddress(), dto.getDetailedAddress());
        return user.getId();
    }
}
