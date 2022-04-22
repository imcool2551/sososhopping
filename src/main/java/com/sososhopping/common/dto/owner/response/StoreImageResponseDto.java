package com.sososhopping.common.dto.owner.response;

import com.sososhopping.entity.store.StoreImage;
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
