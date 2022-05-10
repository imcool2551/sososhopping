package com.sososhopping.entity.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    private String name;

    private String nickname;

    @Column(unique = true, columnDefinition = "char", length = 11)
    private String phone;

    private String streetAddress;

    private String detailedAddress;

    public UserInfo(String name, String nickname, String phone, String streetAddress, String detailedAddress) {
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
    }

    public void updateUserInfo(String name,
                               String phone,
                               String nickname,
                               String streetAddress,
                               String detailedAddress) {

        this.name = name;
        this.phone = phone;
        this.nickname = nickname;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
    }
}
