package com.sososhopping.server.common.dto.owner.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreBusinessStatusResponseDto {
    private Boolean businessStatus;
}