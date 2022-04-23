package com.sososhopping.domain.owner.dto.response;

import com.sososhopping.entity.member.Owner;
import lombok.Data;

@Data
public class OwnerInfoResponse {

    private String name;
    private String phone;
    private String email;

    public OwnerInfoResponse(Owner owner) {
        this.name = owner.getName();
        this.phone = owner.getPhone();
        this.email = owner.getEmail();
    }
}
