package com.sososhopping.server.common.dto.user.response.store;

import com.sososhopping.server.entity.store.StoreBusinessDay;
import lombok.Getter;

@Getter
public class StoreBusinessDayDto {

    private String day;
    private Boolean isOpen;
    private String openTime;
    private String closeTime;

    public StoreBusinessDayDto(StoreBusinessDay storeBusinessDay) {
        day = storeBusinessDay.getDay().getKrDay();
        isOpen = storeBusinessDay.getIsOpen();
        openTime = storeBusinessDay.getOpenTime();
        closeTime = storeBusinessDay.getCloseTime();
    }
}
