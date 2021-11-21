package com.sososhopping.server.common.dto.owner.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreRequestDto {
    private String imgUrl;
    private String name;
    private String description;
    private String phone;
    private String storeType;
    private String extraBusinessDay;
    private Boolean localCurrencyStatus;
    private Boolean deliveryStatus;
    private String streetAddress;
    private String detailedAddress;
    private List<StoreBusinessDayRequestDto> storeBusinessDays;
    private StoreMetaDataRequestDto storeMetaDataRequestDto;
    private String lat; //위도
    private String lng; //경도
}
