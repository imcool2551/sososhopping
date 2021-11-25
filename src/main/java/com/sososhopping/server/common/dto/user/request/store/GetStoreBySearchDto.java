package com.sososhopping.server.common.dto.user.request.store;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
}
