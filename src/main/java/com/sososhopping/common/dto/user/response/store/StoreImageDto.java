package com.sososhopping.common.dto.user.response.store;

import com.sososhopping.entity.store.StoreImage;
import lombok.Getter;

@Getter
public class StoreImageDto {

    private String imgUrl;

    public StoreImageDto(StoreImage storeImage) {
        imgUrl = storeImage.getImgUrl();
    }
}
