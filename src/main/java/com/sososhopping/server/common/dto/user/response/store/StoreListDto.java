package com.sososhopping.server.common.dto.user.response.store;

import com.sososhopping.server.entity.member.InterestStore;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.entity.store.StoreType;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class StoreListDto {

    private Long storeId;
    private StoreType storeType;
    private String name;
    private String imgUrl;
    private Boolean businessStatus;
    private Boolean localCurrencyStatus;
    private Boolean pickupStatus;
    private Boolean deliveryStatus;
    private StoreInfoDto.Coordinate location;
    private Double score;

    public StoreListDto (InterestStore interestStore) {
        storeId = interestStore.getStore().getId();
        storeType = interestStore.getStore().getStoreType();
        name = interestStore.getStore().getName();
        imgUrl = interestStore.getStore().getImgUrl();
        businessStatus = interestStore.getStore().getBusinessStatus();
        localCurrencyStatus = interestStore.getStore().getLocalCurrencyStatus();
        pickupStatus = interestStore.getStore().getPickupStatus();
        deliveryStatus = interestStore.getStore().getDeliveryStatus();
        location = new StoreInfoDto.Coordinate(
                interestStore.getStore().getLocation().getX(),
                interestStore.getStore().getLocation().getY()
        );
        score = interestStore.getStore().getReviews()
                .stream()
                .mapToDouble(review -> review.getScore().doubleValue())
                .average()
                .orElse(0);
    }

    @Getter
    @AllArgsConstructor
    static class Coordinate {
        private double lat;
        private double lng;
    }
}
