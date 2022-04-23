package com.sososhopping.domain.store.dto.request;

import com.sososhopping.common.dto.owner.request.StoreBusinessDayRequestDto;
import com.sososhopping.common.dto.owner.request.StoreMetaDataRequestDto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateStoreDto {

    @NotNull(message = "이름 필수")
    @NotBlank(message = "이름 필수")
    private String name;

//    private String imgUrl;
//    private String description;

    @NotNull(message = "번호 필수")
    @Length(min = 11, max = 11, message = "번호 필수")
    private String phone;
//    private String storeType;
//    private String extraBusinessDay;
    private boolean localCurrencyStatus;
    private boolean deliveryStatus;
    private Integer deliveryCharge;
    private String streetAddress;
    private String detailedAddress;
    private List<StoreBusinessDayRequestDto> storeBusinessDays;
    private StoreMetaDataRequestDto storeMetaDataRequestDto;
    private String lat; //위도
    private String lng; //경도
}
