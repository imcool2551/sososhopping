package com.sososhopping.server.common.dto.owner.response;

import com.sososhopping.server.entity.store.StoreMetaData;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreMetaDataResponseDto {
    private String businessNumber;
    private String representativeName;
    private String businessName;
    private String openingDate;

    public StoreMetaDataResponseDto(StoreMetaData storeMetaData) {
        this.businessNumber = storeMetaData.getBusinessNumber();
        this.representativeName = storeMetaData.getRepresentativeName();
        this.businessName = storeMetaData.getBusinessName();
        this.openingDate = storeMetaData.getOpeningDate();
    }
}
