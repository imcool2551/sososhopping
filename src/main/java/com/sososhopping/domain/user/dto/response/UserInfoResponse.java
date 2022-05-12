package com.sososhopping.domain.user.dto.response;

import com.sososhopping.entity.user.User;
import lombok.Data;

@Data
public class UserInfoResponse {

    private String name;
    private String phone;
    private String email;
    private String nickname;
    private String streetAddress;
    private String detailedAddress;

    public UserInfoResponse(User user) {
        this.name = user.getName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.streetAddress = user.getStreetAddress();
        this.detailedAddress = user.getDetailedAddress();
    }
}
