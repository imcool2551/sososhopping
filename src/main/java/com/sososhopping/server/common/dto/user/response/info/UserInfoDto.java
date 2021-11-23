package com.sososhopping.server.common.dto.user.response.info;

import com.sososhopping.server.entity.member.User;
import lombok.Getter;

@Getter
public class UserInfoDto {

    private String name;
    private String phone;
    private String email;
    private String nickname;
    private String streetAddress;
    private String detailedAddress;

    public UserInfoDto(User user) {
        this.name = user.getName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.streetAddress = user.getStreetAddress();
        this.detailedAddress = user.getDetailedAddress();
    }
}
