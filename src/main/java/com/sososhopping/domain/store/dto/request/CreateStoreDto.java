package com.sososhopping.domain.store.dto.request;

import com.sososhopping.entity.owner.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreateStoreDto {

    @NotNull(message = "이름 필수")
    @NotBlank(message = "이름 필수")
    private String name;

    @NotNull(message = "점포 종류 필수")
    @NotBlank(message = "점포 종류 필수")
    private String storeType;

    @NotNull(message = "번호 필수")
    @Length(min = 11, max = 11, message = "번호 형식 오류")
    private String phone;

    @NotNull(message = "위도 필수")
    private Double lat;

    @NotNull(message = "경도 필수")
    private Double lng;

    @NotNull(message = "도로 주소 필수")
    @NotBlank(message = "도로 주소 필수")
    private String streetAddress;

    @NotNull(message = "상세 주소 필수")
    @NotBlank(message = "상세 주소 필수")
    private String detailedAddress;

    @NotNull(message = "픽업 지원 여부 필수")
    private Boolean pickupStatus;

    @NotNull(message = "배송 지원 여부 필수")
    private Boolean deliveryStatus;

    @NotNull(message = "포인트 제도 운영 여부 필수")
    private Boolean pointPolicyStatus;

    private String imgUrl;

    private String description;

    private String extraBusinessDay;

    @Min(value = 0, message = "최소 주문 금액은 0원 이상")
    private Integer minimumOrderPrice;

    @Min(value = 0, message = "적립율은 0% 이상")
    private Double saveRate;

    @Min(value = 0, message = "배송 금액은 0원 이상")
    private Integer deliveryCharge;

    @Valid
    @NotNull(message = "사업자 정보 필수")
    private StoreMetadataDto metadata;

    @Valid
    @Size(min = 7, max = 7, message = "모든 요일 영업 여부 필수")
    private List<StoreBusinessDayDto> days;

    public Store toStore(Owner owner) {
        return Store.builder()
                .owner(owner)
                .name(this.getName())
                .storeType(StoreType.ofKrName(this.getStoreType()))
                .phone(this.getPhone())
                .lat(BigDecimal.valueOf(this.getLat()))
                .lng(BigDecimal.valueOf(this.getLng()))
                .streetAddress(this.getStreetAddress())
                .detailedAddress(this.getDetailedAddress())
                .storeStatus(StoreStatus.PENDING)
                .isOpen(false)
                .pickupStatus(this.getPickupStatus())
                .deliveryStatus(this.getDeliveryStatus())
                .pointPolicyStatus(this.getPointPolicyStatus())
                .imgUrl(this.getImgUrl())
                .description(this.getDescription())
                .extraBusinessDay(this.getExtraBusinessDay())
                .minimumOrderPrice(this.getMinimumOrderPrice())
                .saveRate(BigDecimal.valueOf(this.getSaveRate()))
                .deliveryCharge(this.getDeliveryCharge())
                .build();
    }

    public StoreMetadata toStoreMetadata(Store store) {
        return StoreMetadata.builder()
                .store(store)
                .businessNumber(this.getMetadata().getBusinessNumber())
                .representativeName(this.getMetadata().getRepresentativeName())
                .businessName(this.getMetadata().getBusinessName())
                .openingDate(this.getMetadata().getOpeningDate())
                .build();
    }

    public List<StoreBusinessDay> toStoreBusinessDays(Store store) {
        return days.stream()
                .map(day -> StoreBusinessDay.builder()
                        .store(store)
                        .day(Day.krDayOf(day.getDay()))
                        .isOpen(day.isOpen)
                        .openTime(day.openTime)
                        .closeTime(day.closeTime)
                        .build())
                .collect(Collectors.toList());
    }

    public String getBusinessNumber() {
        return metadata.getBusinessNumber();
    }

    @Data
    static class StoreMetadataDto {

        @NotNull(message = "사업자 번호 필수")
        @Length(min = 10, max = 10, message = "사업자 번호는 10자리")
        private String businessNumber;

        @NotNull(message = "대표자 이름 필수")
        @NotBlank(message = "대표자 이름 필수")
        private String representativeName;

        @NotNull(message = "상호명 필수")
        @NotBlank(message = "상호명 필수")
        private String businessName;

        @NotNull
        private LocalDateTime openingDate;
    }

    @Data
    static class StoreBusinessDayDto {
        @NotNull(message = "요일 필수")
        @NotBlank(message = "요일 필수")
        private String day;

        @NotNull(message = "영업 여부 필수")
        private Boolean isOpen;

        private String openTime;

        private String closeTime;
    }

}
