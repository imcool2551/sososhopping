package com.sososhopping.domain.store.dto.owner.request;

import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.store.Store;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateItemDto {

    @NotNull(message = "물품 이름 필수")
    @NotBlank(message = "물품 이름 필수")
    private String name;

    private String description;

    private String purchaseUnit;

    private String imgUrl;

    @NotNull(message = "물품 가격 필수")
    @Min(value = 1, message = "물품 기격 0원 이상")
    private Integer price;

    @NotNull(message = "물품 판매 여부 필수")
    private Boolean onSale;

    public Item toEntity(Store store) {
        return Item.builder()
                .store(store)
                .name(name)
                .description(description)
                .purchaseUnit(purchaseUnit)
                .imgUrl(imgUrl)
                .price(price)
                .onSale(onSale)
                .build();
    }
}
