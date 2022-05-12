package com.sososhopping.domain.user.controller;

import com.sososhopping.domain.user.dto.request.UserInfoUpdateDto;
import com.sososhopping.domain.user.dto.response.UserInfoResponse;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.domain.user.service.UserInfoService;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final UserRepository userRepository;

    @GetMapping("/users/my/info")
    public UserInfoResponse getUserInfo(Authentication authentication) {

        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        return new UserInfoResponse(user);
    }

    @PatchMapping("/users/my/info")
    public void updateUserInfo(Authentication authentication,
                               @RequestBody @Valid UserInfoUpdateDto dto) {

        Long userId = Long.parseLong(authentication.getName());
        userInfoService.updateUserInfo(userId, dto);
    }
}
