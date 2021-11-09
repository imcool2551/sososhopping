package com.sososhopping.server.common.dto.owner.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreItemRequestDto {
    private String name;
    private String description;
    private String purchaseUnit;
    private Integer price;
    private Boolean saleStatus;
}
