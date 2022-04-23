package com.sososhopping.common.dto.owner.response;

import com.sososhopping.entity.store.Item;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreItemResponseDto {
    private Long storeId;
    private Long id;
    private String name;
    private String description;
    private String purchaseUnit;
    private String imgUrl;
    private Integer price;
    private Boolean saleStatus;

    public StoreItemResponseDto(Item item, Long storeId) {
        this.storeId = storeId;
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.purchaseUnit = item.getPurchaseUnit();
        this.imgUrl = item.getImgUrl();
        this.price = item.getPrice();
        this.saleStatus = item.isOnSale();
    }
}
