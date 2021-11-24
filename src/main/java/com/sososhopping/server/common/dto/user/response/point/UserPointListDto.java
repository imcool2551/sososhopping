package com.sososhopping.server.common.dto.user.response.point;

import com.sososhopping.server.entity.member.UserPoint;
import com.sososhopping.server.entity.store.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserPointListDto {

    private List<Point> interestStorePoints = new ArrayList();
    private List<Point> storePoints = new ArrayList<>();

    public UserPointListDto(List<UserPoint> userPoints, List<Store> interestStores) {
        userPoints
                .forEach(userPoint -> {
                    Store store = userPoint.getStore();
                    Point point = new Point(store.getId(), store.getName(), store.getImgUrl(), userPoint.getPoint());
                    if (interestStores.contains(store)) {
                        interestStorePoints.add(point);
                    } else {
                        storePoints.add(point);
                    }
                });
    }

    @Data
    static class Point {
        private final Long storeId;
        private final String storeName;
        private final String imgUrl;
        private final Integer point;
    }
}
