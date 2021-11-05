package com.sososhopping.server.common.dto.user.response.store;

import com.sososhopping.server.entity.store.Item;
import lombok.Getter;

@Getter
public class ItemDto {

    private Long itemId;
    private String name;
    private String description;
    private String purchaseUnit;
    private String imgUrl;
    private Integer price;
    private Boolean saleStatus;

    public ItemDto(Item item) {
        itemId = item.getId();
        name = item.getName();
        description = item.getDescription();
        purchaseUnit = item.getPurchaseUnit();
        imgUrl = item.getImgUrl();
        price = item.getPrice();
        saleStatus = item.getSaleStatus();
    }
}
