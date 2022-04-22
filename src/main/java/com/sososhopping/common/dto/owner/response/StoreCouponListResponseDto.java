package com.sososhopping.common.dto.owner.response;

import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreCouponListResponseDto {
    List<StoreCouponResponseDto> expected;
    List<StoreCouponResponseDto> being;
}
