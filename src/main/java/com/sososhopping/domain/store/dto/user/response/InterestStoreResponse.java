package com.sososhopping.domain.store.dto.user.response;

import com.sososhopping.entity.store.Store;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InterestStoreResponse {

    private Long storeId;
    private Long ownerId;
    private String storeType;
    private String storeName;
    private String description;
    private String phone;
    private String imgUrl;
    private BigDecimal lat;
    private BigDecimal lng;
    private double score;

    public InterestStoreResponse(Store store) {
        storeId = store.getId();
        ownerId = store.getOwner().getId();
        storeType = store.getStoreType().getKrName();
        storeName = store.getName();
        description = store.getDescription();
        phone = store.getPhone();
        imgUrl = store.getImgUrl();
        lat = store.getLat();
        lng = store.getLng();
        score = calculateScore(store);
    }

    private double calculateScore(Store store) {
        return store.getReviews()
                .stream()
                .mapToDouble(review -> review.getScore().doubleValue())
                .average()
                .orElse(0);
    }
}
