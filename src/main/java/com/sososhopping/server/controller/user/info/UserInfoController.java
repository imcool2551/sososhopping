package com.sososhopping.server.controller.user.info;

import com.sososhopping.server.common.dto.user.request.info.UserInfoUpdateDto;
import com.sososhopping.server.common.dto.user.response.info.UserInfoDto;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.service.user.info.UserInfoService;
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

    @GetMapping("/api/v1/users/info")
    public UserInfoDto getUserInfo(Authentication authentication) {

        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

       return new UserInfoDto(user);
    }

    @PutMapping("/api/v1/users/info")
    public UserInfoDto updateUserInfo(
            Authentication authentication,
            @RequestBody @Valid UserInfoUpdateDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());

        User user = userInfoService.updateUserInfo(userId, dto);
        return new UserInfoDto(user);
    }
}