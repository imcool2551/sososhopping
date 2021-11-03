package com.sososhopping.server.common.dto.user.response.store;

import com.sososhopping.server.entity.store.StoreImage;
import lombok.Getter;

@Getter
public class StoreImageDto {

    private String imgUrl;

    public StoreImageDto(StoreImage storeImage) {
        imgUrl = storeImage.getImgUrl();
    }
}
