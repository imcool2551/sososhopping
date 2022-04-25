package com.sososhopping.common.dto.owner.response;

import com.sososhopping.entity.owner.Store;
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
    private Integer deliveryCharge;
    private String streetAddress;
    private String detailedAddress;
    private List<StoreBusinessDayResponseDto> storeBusinessDays;

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.imgUrl = store.getImgUrl();
        this.name = store.getName();
        this.description = store.getDescription();
        this.phone = store.getPhone();
        this.storeType = store.getStoreType().getKrType();
        this.storeStatus = store.getStoreStatus().name();
        this.extraBusinessDay = store.getExtraBusinessDay();
        this.deliveryCharge = store.getDeliveryCharge();
        this.streetAddress = store.getStreetAddress();
        this.detailedAddress = store.getDetailedAddress();
        this.storeBusinessDays = store.getStoreBusinessDays()
                .stream()
                .map(sbd -> new StoreBusinessDayResponseDto(sbd))
                .collect(Collectors.toList());
    }
}
