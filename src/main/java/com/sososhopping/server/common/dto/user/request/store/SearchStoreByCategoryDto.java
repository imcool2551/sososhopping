package com.sososhopping.server.common.dto.user.request.store;

import com.sososhopping.server.entity.store.StoreType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchStoreByCategoryDto {

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotNull
    private Double radius;

    @NotNull
    private StoreType type;
}
