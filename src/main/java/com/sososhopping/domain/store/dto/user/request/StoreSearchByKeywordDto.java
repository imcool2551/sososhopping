package com.sososhopping.domain.store.dto.user.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StoreSearchByKeywordDto {

    @NotNull(message = "위도 필수")
    private Double lat;

    @NotNull(message = "경도 필수")
    private Double lng;

    @NotNull(message = "반경 필수")
    private Double radius;

    @NotNull(message = "검색 조건 필수")
    private StoreSearchType type;

    @NotNull(message = "검색어 필수")
    @NotBlank(message = "검색어 필수")
    private String q;

    @NotNull(message = "offset 필수")
    @Min(0)
    private Integer offset = 0;

    @NotNull(message = "limit 필수")
    @Min(0)
    @Max(100)
    private Integer limit = 10;
}
