package com.sososhopping.domain.orders.dto.user.response;

import com.sososhopping.entity.user.Cart;
import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.store.Store;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyCartResponse {

    private Long storeId;
    private String storeName;
    private int totalPrice = 0;
    private List<ItemDto> items = new ArrayList<>();

    public MyCartResponse(Store store, List<Cart> carts) {
        storeId = store.getId();
        storeName = store.getName();

        carts.forEach(cart -> {
            Item item = cart.getItem();
            totalPrice += item.getPrice() * cart.getQuantity();
            items.add(new ItemDto(item, cart.getQuantity()));
        });
    }

    @Data
    static class ItemDto {
        private Long itemId;
        private String itemName;
        private String imgUrl;
        private String description;
        private int price;
        private boolean saleStatus;
        private int quantity;

        public ItemDto(Item item, int quantity) {
            this.itemId = item.getId();
            this.itemName = item.getName();
            this.imgUrl = item.getImgUrl();
            this.description = item.getDescription();
            this.price = item.getPrice();
            this.saleStatus = item.isOnSale();
            this.quantity = quantity;
        }
    }
}
