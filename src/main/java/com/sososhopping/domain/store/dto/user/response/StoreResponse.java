package com.sososhopping.domain.store.dto.user.response;

import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.StoreBusinessDay;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class StoreResponse {

    private Long storeId;
    private Long ownerId;
    private String storeType;
    private String name;
    private String imgUrl;
    private String description;
    private String extraBusinessDay;
    private String phone;
    private boolean isOpen;
    private boolean pickupStatus;
    private boolean deliveryStatus;
    private int deliveryCharge;
    private BigDecimal saveRate;
    private String streetAddress;
    private String detailedAddress;
    private List<StoreBusinessDayDto> businessDays;
    private BigDecimal lat;
    private BigDecimal lng;
    private double score;
    private boolean isInterestStore;
    private int point;

    public StoreResponse(Store store, boolean isInterestStore, int point) {
        this.storeId = store.getId();
        this.ownerId = store.getOwner().getId();
        this.storeType = store.getStoreType().getKrName();
        this.name = store.getName();
        this.imgUrl = store.getImgUrl();
        this.description = store.getDescription();
        this.extraBusinessDay = store.getExtraBusinessDay();
        this.phone = store.getPhone();
        this.isOpen = store.isOpen();
        this.pickupStatus = store.isPickupStatus();
        this.deliveryStatus = store.isDeliveryStatus();
        this.deliveryCharge = store.getDeliveryCharge();
        this.saveRate = store.getSaveRate();
        this.streetAddress = store.getStreetAddress();
        this.detailedAddress = store.getDetailedAddress();
        this.businessDays = mapBusinessDays(store);
        this.lat = store.getLat();
        this.lng = store.getLng();
        this.score = mapScore(store);
        this.isInterestStore = isInterestStore;
        this.point = point;
    }

    private List<StoreBusinessDayDto> mapBusinessDays(Store store) {
        return businessDays = store.getStoreBusinessDays()
                .stream()
                .map(StoreBusinessDayDto::new)
                .collect(Collectors.toList());
    }

    private double mapScore(Store store) {
        return store.getReviews()
                .stream()
                .mapToDouble(reviews -> reviews.getScore().doubleValue())
                .average()
                .orElse(0);
    }

    @Data
    static class StoreBusinessDayDto {

        private String day;
        private boolean isOpen;
        private String openTime;
        private String closeTime;

        public StoreBusinessDayDto(StoreBusinessDay storeBusinessDay) {
            this.day = storeBusinessDay.getDay().getKrDay();
            this.isOpen = storeBusinessDay.isOpen();
            this.openTime = storeBusinessDay.getOpenTime();
            this.closeTime = storeBusinessDay.getCloseTime();
        }
    }
}
