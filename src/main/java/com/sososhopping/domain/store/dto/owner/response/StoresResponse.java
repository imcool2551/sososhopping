package com.sososhopping.domain.store.dto.owner.response;

import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.StoreStatus;
import lombok.Data;

@Data
public class StoresResponse {

    private Long id;
    private String name;
    private String imgUrl;
    private String description;
    private StoreStatus storeStatus;

    public StoresResponse(Store store) {
        id = store.getId();
        name = store.getName();
        imgUrl = store.getImgUrl();
        description = store.getDescription();
        storeStatus = store.getStoreStatus();
    }
}
