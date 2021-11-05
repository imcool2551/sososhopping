package com.sososhopping.server.common.dto.owner.response;

import lombok.*;

@Builder
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreListResponseDto {
    private Long id;
    private String name;
    private String imgUrl;
    private String description;
    private String storeStatus;
}
