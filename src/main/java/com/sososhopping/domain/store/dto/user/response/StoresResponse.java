package com.sososhopping.domain.store.dto.user.response;

import com.sososhopping.entity.store.Store;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class StoresResponse {

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
    private boolean isInterestStore;
    private double distance;

    public StoresResponse(Store store, List<Store> interestStores, Map<Long, Double> idToDistance) {
        storeId = store.getId();
        ownerId = store.getOwner().getId();
        storeType = store.getStoreType().getKrName();
        storeName = store.getName();
        description = store.getDescription();
        phone = store.getPhone();
        imgUrl = store.getImgUrl();
        lat = store.getLat();
        lng = store.getLng();
        score = mapScore(store);
        isInterestStore = interestStores.contains(store);
        distance = idToDistance.get(store.getId());
    }

    private double mapScore(Store store) {
        return store.getReviews()
                .stream()
                .mapToDouble(review -> review.getScore().doubleValue())
                .average()
                .orElse(0);
    }
}
