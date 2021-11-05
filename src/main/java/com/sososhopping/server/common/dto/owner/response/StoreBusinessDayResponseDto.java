package com.sososhopping.server.common.dto.owner.response;

import com.sososhopping.server.entity.store.StoreBusinessDay;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreBusinessDayResponseDto {
    private Long id;
    private String day;
    private Boolean isOpen;
    private String openTime;
    private String closeTime;

    public StoreBusinessDayResponseDto(StoreBusinessDay storeBusinessDay) {
        this.id = storeBusinessDay.getId();
        this.day = storeBusinessDay.getDay().getKrDay();
        this.isOpen = storeBusinessDay.getIsOpen();
        this.openTime = storeBusinessDay.getOpenTime();
        this.closeTime = storeBusinessDay.getCloseTime();
    }
}
