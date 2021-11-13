package com.sososhopping.server.common.dto.user.response.store;

import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.entity.store.StoreType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StoreInfoDto {
    private Long storeId;
    private String storeType;
    private String name;
    private String imgUrl;
    private String description;
    private String extraBusinessDay;
    private String phone;
    private Boolean businessStatus;
    private Boolean localCurrencyStatus;
    private Boolean pickupStatus;
    private Boolean deliveryStatus;
    private BigDecimal saveRate;
    private String streetAddress;
    private String detailedAddress;
    private List<StoreBusinessDayDto> businessDays;
    private List<StoreImageDto> storeImages;
    private Coordinate location;
    private Double score;
    private Boolean isInterestStore;

    public StoreInfoDto(Store store, boolean isInterestStore) {
        storeId = store.getId();
        storeType = store.getStoreType().getKrType();
        name = store.getName();
        imgUrl = store.getImgUrl();
        description = store.getDescription();
        extraBusinessDay = store.getExtraBusinessDay();
        phone = store.getPhone();
        businessStatus = store.getBusinessStatus();
        localCurrencyStatus = store.getLocalCurrencyStatus();
        pickupStatus = store.getPickupStatus();
        deliveryStatus = store.getDeliveryStatus();
        saveRate = store.getSaveRate();
        streetAddress = store.getStreetAddress();
        detailedAddress = store.getDetailedAddress();
        businessDays = store.getStoreBusinessDays()
                .stream()
                .map(storeBusinessDay -> new StoreBusinessDayDto(storeBusinessDay))
                .collect(Collectors.toList());
        storeImages = store.getStoreImages()
                .stream()
                .map(storeImage -> new StoreImageDto(storeImage))
                .collect(Collectors.toList());
        location = new Coordinate(store.getLocation().getX(), store.getLocation().getY());
        score = store.getReviews()
                .stream()
                .mapToDouble(reviews -> reviews.getScore().doubleValue())
                .average()
                .orElse(0);
        this.isInterestStore = isInterestStore;
    }

    @Getter
    @AllArgsConstructor
    static class Coordinate {
        private double lat;
        private double lng;
    }
}
