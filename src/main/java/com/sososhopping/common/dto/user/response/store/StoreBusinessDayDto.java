package com.sososhopping.common.dto.user.response.store;

import com.sososhopping.entity.store.StoreBusinessDay;
import lombok.Getter;

@Getter
public class StoreBusinessDayDto {

    private String day;
    private boolean isOpen;
    private String openTime;
    private String closeTime;

    public StoreBusinessDayDto(StoreBusinessDay storeBusinessDay) {
        day = storeBusinessDay.getDay().getKrDay();
        isOpen = storeBusinessDay.isOpen();
        openTime = storeBusinessDay.getOpenTime();
        closeTime = storeBusinessDay.getCloseTime();
    }
}
