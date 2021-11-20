package com.sososhopping.server.common.dto.user.response.store;

import com.querydsl.core.annotations.QueryProjection;
import com.sososhopping.server.entity.member.InterestStore;
import com.sososhopping.server.entity.store.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@Getter
public class StoreListDto {

    private final Long storeId;
    private final String storeType;
    private final String name;
    private final String description;
    private final String phone;
    private final String imgUrl;
    private final Boolean businessStatus;
    private final Boolean localCurrencyStatus;
    private final Boolean pickupStatus;
    private final Boolean deliveryStatus;
    private final Coordinate location;
    private final Double score;
    private final Boolean isInterestStore;
    private final Double distance;

    public StoreListDto (
            Store store,
            List<InterestStore> interestStores,
            Map<Long, Double> idToDistanceMap
    ) {
        storeId = store.getId();
        storeType = store.getStoreType().getKrType();
        name = store.getName();
        description = store.getDescription();
        phone = store.getPhone();
        imgUrl = store.getImgUrl();
        businessStatus = store.getBusinessStatus();
        localCurrencyStatus = store.getLocalCurrencyStatus();
        pickupStatus = store.getPickupStatus() ;
        deliveryStatus = store.getDeliveryStatus();
        location = new Coordinate(
                store.getLat().doubleValue(),
                store.getLng().doubleValue()
        );
        score = store.getReviews()
                .stream()
                .mapToDouble(review -> review.getScore().doubleValue())
                .average()
                .orElse(0);
        isInterestStore = interestStores.stream()
                .anyMatch(interestStore ->
                        Objects.equals(store.getId(), interestStore.getStore().getId())
                );
        distance = idToDistanceMap.get(store.getId());
    }

    public StoreListDto (InterestStore interestStore) {
        storeId = interestStore.getStore().getId();
        storeType = interestStore.getStore().getStoreType().getKrType();
        name = interestStore.getStore().getName();
        description = interestStore.getStore().getDescription();
        phone = interestStore.getStore().getPhone();
        imgUrl = interestStore.getStore().getImgUrl();
        businessStatus = interestStore.getStore().getBusinessStatus();
        localCurrencyStatus = interestStore.getStore().getLocalCurrencyStatus();
        pickupStatus = interestStore.getStore().getPickupStatus();
        deliveryStatus = interestStore.getStore().getDeliveryStatus();
        location = new Coordinate(
                interestStore.getStore().getLat().doubleValue(),
                interestStore.getStore().getLng().doubleValue()
        );
        score = interestStore.getStore().getReviews()
                .stream()
                .mapToDouble(review -> review.getScore().doubleValue())
                .average()
                .orElse(0);
        isInterestStore = true;
        distance = null;
    }

    @Getter
    @AllArgsConstructor
    static class Coordinate {
        private double lat;
        private double lng;
    }
}
