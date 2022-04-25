package com.sososhopping.common.dto.user.response.store;

import com.sososhopping.entity.owner.StoreBusinessDay;
import lombok.Getter;

@Getter
public class StoreBusinessDayDto {

    private String day;
    private Boolean isOpen;
    private String openTime;
    private String closeTime;

    public StoreBusinessDayDto(StoreBusinessDay storeBusinessDay) {
        day = storeBusinessDay.getDay().getKrDay();
        openTime = storeBusinessDay.getOpenTime();
        closeTime = storeBusinessDay.getCloseTime();
    }
}
