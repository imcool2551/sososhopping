package com.sososhopping.server.common.dto.owner.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreReviewListResponseDto {
    private BigDecimal averageScore;
    private List<StoreReviewResponseDto> reviews;
}
