package com.sososhopping.domain.point.dto.response;

import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Store;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyPointsResponse {

    private List<MyPointResponse> storePoints = new ArrayList<>();
    private List<MyPointResponse> interestStorePoints = new ArrayList();

    public MyPointsResponse(List<UserPoint> userPoints, List<Store> interestStores) {
        userPoints.stream()
                .filter(userPoint -> !interestStores.contains(userPoint.getStore()))
                .map(userPoint -> new MyPointResponse(userPoint.getStore(), userPoint.getPoint()))
                .forEach(storePoints::add);

        userPoints.stream()
                .filter(userPoint -> interestStores.contains(userPoint.getStore()))
                .map(userPoint -> new MyPointResponse(userPoint.getStore(), userPoint.getPoint()))
                .forEach(interestStorePoints::add);
    }

    @Data
    static class MyPointResponse {
        private final Long storeId;
        private final String storeName;
        private final String imgUrl;
        private final int point;

        public MyPointResponse(Store store, int point) {
            this.storeId = store.getId();
            this.storeName = store.getName();
            this.imgUrl = store.getImgUrl();
            this.point = point;
        }
    }
}
