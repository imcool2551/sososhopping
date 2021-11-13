package com.sososhopping.server.common.dto.user.response.point;

import com.sososhopping.server.entity.member.UserPoint;
import com.sososhopping.server.entity.store.Store;
import lombok.Getter;

import java.util.List;

@Getter
public class UserPointListDto {

    private Long storeId;
    private String storeName;
    private String imgUrl;
    private Integer point;
    private Boolean isInterestStore;

    public UserPointListDto(UserPoint userPoint, List<Store> interestStores) {
        storeId = userPoint.getStore().getId();
        storeName = userPoint.getStore().getName();
        imgUrl = userPoint.getStore().getImgUrl();
        point = userPoint.getPoint();
        isInterestStore = interestStores.contains(userPoint.getStore());
    }
}
