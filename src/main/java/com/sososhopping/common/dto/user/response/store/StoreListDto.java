package com.sososhopping.common.dto.user.response.store;

import com.sososhopping.entity.member.InterestStore;
import com.sososhopping.entity.store.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@Getter
public class StoreListDto implements Comparable<StoreListDto> {

    private final Long storeId;
    private final Long ownerId;
    private final String storeType;
    private final String name;
    private final String description;
    private final String phone;
    private final String imgUrl;
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
        ownerId = store.getOwner().getId();
        storeType = store.getStoreType().getKrType();
        name = store.getName();
        description = store.getDescription();
        phone = store.getPhone();
        imgUrl = store.getImgUrl();
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
        ownerId = interestStore.getStore().getOwner().getId();
        storeType = interestStore.getStore().getStoreType().getKrType();
        name = interestStore.getStore().getName();
        description = interestStore.getStore().getDescription();
        phone = interestStore.getStore().getPhone();
        imgUrl = interestStore.getStore().getImgUrl();
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

    @Override
    public int compareTo(StoreListDto o) {
        if (this.distance > o.distance) {
            return 1;
        } else {
            return -1;
        }
    }

    @Getter
    @AllArgsConstructor
    static class Coordinate {
        private double lat;
        private double lng;
    }
}
