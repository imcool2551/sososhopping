package com.sososhopping.server.common.dto.owner.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreAccountingRequestDto {
    private Integer amount;
    private String description;
    private String date;
}
