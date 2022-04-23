package com.sososhopping.common.dto.user.response.order;

import com.sososhopping.entity.member.Cart;
import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.store.Store;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserCartDto {

    private Long storeId;
    private String storeName;
    private Integer totalPrice = 0;
    private List<ItemDto> items = new ArrayList<>();

    public UserCartDto(Store store, List<Cart> carts) {
        storeId = store.getId();
        storeName = store.getName();

        carts.forEach(cart -> {
            Item item = cart.getItem();
            Integer quantity = cart.getQuantity();
            totalPrice += item.getPrice() * quantity;
            items.add(new ItemDto(item, quantity));
        });
    }

    @Data
    static class ItemDto {
        private Long itemId;
        private String itemName;
        private String imgUrl;
        private String description;
        private Integer price;
        private boolean saleStatus;
        private Integer num;

        public ItemDto(Item item, Integer n) {
            itemId = item.getId();
            itemName = item.getName();
            imgUrl = item.getImgUrl();
            description = item.getDescription();
            price = item.getPrice();
            saleStatus = item.isOnSale();
            num = n;
        }
    }
}
