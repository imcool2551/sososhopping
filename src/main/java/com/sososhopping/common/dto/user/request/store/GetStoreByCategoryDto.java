package com.sososhopping.common.dto.user.request.store;

import com.sososhopping.entity.owner.StoreType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetStoreByCategoryDto {

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotNull
    private Double radius;

    @NotNull
    private StoreType type;

    private Integer offset = 0;
}