package com.sososhopping.common.dto.user.request.store;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ToggleStoreLikeDto {

    @NotNull
    private Long storeId;
}
