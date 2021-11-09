package com.sososhopping.server.common.dto.owner.response;

import com.sososhopping.server.entity.store.Store;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreResponseDto {
    private Long id;
    private String imgUrl;
    private String name;
    private String description;
    private String phone;
    private String storeType;
    private String storeStatus;
    private String extraBusinessDay;
    private Boolean businessStatus;
    private Boolean localCurrencyStatus;
    private Boolean deliveryStatus;
    private String streetAddress;
    private String detailedAddress;
    private List<StoreBusinessDayResponseDto> storeBusinessDays;
    private List<StoreImageResponseDto> storeImages;
    private StoreMetaDataResponseDto storeMetaDataResponseDto;

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.imgUrl = store.getImgUrl();
        this.name = store.getName();
        this.description = store.getDescription();
        this.phone = store.getPhone();
        this.storeType = store.getStoreType().name();
        this.storeStatus = store.getStoreStatus().name();
        this.extraBusinessDay = store.getExtraBusinessDay();
        this.businessStatus = store.getBusinessStatus();
        this.localCurrencyStatus = store.getLocalCurrencyStatus();
        this.deliveryStatus = store.getDeliveryStatus();
        this.streetAddress = store.getStreetAddress();
        this.detailedAddress = store.getDetailedAddress();
        this.storeBusinessDays = store.getStoreBusinessDays()
                .stream()
                .map(sbd -> new StoreBusinessDayResponseDto(sbd))
                .collect(Collectors.toList());
        this.storeImages = store.getStoreImages()
                .stream()
                .map(storeImage -> new StoreImageResponseDto(storeImage))
                .collect(Collectors.toList());
        this.storeMetaDataResponseDto = new StoreMetaDataResponseDto(store.getStoreMetaData());
    }
}