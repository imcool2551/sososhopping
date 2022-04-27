package com.sososhopping.domain.store.dto.response;

import com.sososhopping.entity.store.Item;
import lombok.Data;

@Data
public class StoreItemResponse {

    private Long storeId;
    private Long itemId;
    private String name;
    private String description;
    private String purchaseUnit;
    private String imgUrl;
    private int price;
    private boolean saleStatus;

    public StoreItemResponse(Long storeId, Item item) {
        this.storeId = storeId;
        this.itemId = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.purchaseUnit = item.getPurchaseUnit();
        this.imgUrl = item.getImgUrl();
        this.price = item.getPrice();
        this.saleStatus = item.isOnSale();
    }
}
