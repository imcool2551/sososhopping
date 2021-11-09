package com.sososhopping.server.common.dto.owner.response;

import com.sososhopping.server.entity.store.Store;
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

    public StoreListResponseDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.imgUrl = store.getImgUrl();
        this.description = store.getDescription();
        this.storeStatus = store.getStoreStatus().toString();
    }
}
