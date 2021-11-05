package com.sososhopping.server.common.dto.owner.response;

import com.sososhopping.server.entity.store.StoreImage;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreImageResponseDto {
    private Long id;
    private String imgUrl;

    public StoreImageResponseDto(StoreImage storeImage) {
        this.id = storeImage.getId();
        this.imgUrl = storeImage.getImgUrl();
    }
}
