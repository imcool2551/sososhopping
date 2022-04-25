package com.sososhopping.common.dto.owner.response;

import com.sososhopping.entity.store.StoreMetadata;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreMetaDataResponseDto {
    private String businessNumber;
    private String representativeName;
    private String businessName;

    public StoreMetaDataResponseDto(StoreMetadata storeMetadata) {
        this.businessNumber = storeMetadata.getBusinessNumber();
        this.representativeName = storeMetadata.getRepresentativeName();
        this.businessName = storeMetadata.getBusinessName();
    }
}
