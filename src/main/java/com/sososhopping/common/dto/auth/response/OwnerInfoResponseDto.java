package com.sososhopping.common.dto.auth.response;

import com.sososhopping.entity.member.Owner;
import lombok.Data;

@Data
public class OwnerInfoResponseDto {

    private final String email;
    private final String name;
    private final String phone;

    public OwnerInfoResponseDto(Owner owner) {
        email = owner.getEmail();
        name = owner.getName();
        phone = owner.getPhone();
    }
}
