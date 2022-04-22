package com.sososhopping.domain.user.controller;

import com.sososhopping.common.dto.user.request.info.UserInfoUpdateDto;
import com.sososhopping.domain.user.dto.response.UserInfoResponse;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.domain.user.service.UserInfoService;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final UserRepository userRepository;

    @GetMapping("/users/info")
    public UserInfoResponse getUserInfo(Authentication authentication) {

        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        return new UserInfoResponse(user);
    }

    @PutMapping("/users/info")
    public UserInfoResponse updateUserInfo(
            Authentication authentication,
            @RequestBody @Valid UserInfoUpdateDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());

        User user = userInfoService.updateUserInfo(userId, dto);
        return new UserInfoResponse(user);
    }
}
