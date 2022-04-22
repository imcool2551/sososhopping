package com.sososhopping.common.dto.user.request.store;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetStoreBySearchDto {

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotNull
    private Double radius;

    @NotNull
    private StoreSearchType type;

    @NotNull
    private String q;

    private Integer offset = 0;
}
