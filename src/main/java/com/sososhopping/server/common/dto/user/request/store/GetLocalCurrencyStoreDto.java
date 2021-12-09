package com.sososhopping.server.common.dto.user.request.store;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetLocalCurrencyStoreDto {

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotNull
    private Double radius;

    private Integer offset = 0;
}
