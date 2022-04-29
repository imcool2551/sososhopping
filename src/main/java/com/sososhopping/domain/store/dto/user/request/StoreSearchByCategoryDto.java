package com.sososhopping.domain.store.dto.user.request;

import com.sososhopping.entity.store.StoreType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StoreSearchByCategoryDto {

    @NotNull(message = "위도 필수")
    private Double lat;

    @NotNull(message = "경도 필수")
    private Double lng;

    @NotNull(message = "반경 필수")
    private Double radius;

    @NotNull(message = "점포 종류 필수")
    private StoreType type;

    @NotNull(message = "offset 필수")
    private Integer offset = 0;

    @NotNull(message = "limit 필수")
    private Integer limit = 10;
}
