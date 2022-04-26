package com.sososhopping.domain.store.dto.response;

import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.StoreBusinessDay;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class StoreResponse {

    private Long id;
    private String imgUrl;
    private String name;
    private String description;
    private String phone;
    private String storeType;
    private String storeStatus;
    private String extraBusinessDay;
    private Integer deliveryCharge;
    private String streetAddress;
    private String detailedAddress;
    private List<StoreBusinessDayResponse> businessDays;

    public StoreResponse(Store store) {
        id = store.getId();
        imgUrl = store.getImgUrl();
        name = store.getName();
        description = store.getDescription();
        phone = store.getPhone();
        storeType = store.getStoreType().getKrName();
        storeStatus = store.getStoreStatus().name();
        extraBusinessDay = store.getExtraBusinessDay();
        deliveryCharge = store.getDeliveryCharge();
        streetAddress = store.getStreetAddress();
        detailedAddress = store.getDetailedAddress();
        businessDays = store.getStoreBusinessDays()
                .stream()
                .map(StoreBusinessDayResponse::new)
                .collect(Collectors.toList());
    }

    @Data
    static class StoreBusinessDayResponse {

        private String day;
        private boolean isOpen;
        private String openTime;
        private String closeTime;

        public StoreBusinessDayResponse(StoreBusinessDay storeBusinessDay) {
            day = storeBusinessDay.getDay().getKrDay();
            isOpen = storeBusinessDay.isOpen();
            openTime = storeBusinessDay.getOpenTime();
            closeTime = storeBusinessDay.getCloseTime();
        }
    }
}
