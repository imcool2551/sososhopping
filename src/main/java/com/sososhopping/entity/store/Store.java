package com.sososhopping.entity.store;

import com.sososhopping.common.dto.owner.request.StoreBusinessDayRequestDto;
import com.sososhopping.common.dto.owner.request.StorePointPolicyRequestDto;
import com.sososhopping.common.dto.owner.request.StoreRequestDto;
import com.sososhopping.entity.BaseTimeEntity;
import com.sososhopping.entity.member.Review;
import com.sososhopping.entity.owner.Owner;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    private String name;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @Column(columnDefinition = "char", length = 11)
    private String phone;

    private BigDecimal lat;

    private BigDecimal lng;

    private String streetAddress;

    private String detailedAddress;

    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    @Column(columnDefinition = "tinyint")
    private boolean isOpen;

    @Column(columnDefinition = "tinyint")
    private boolean pickupStatus;

    @Column(columnDefinition = "tinyint")
    private boolean deliveryStatus;

    @Column(columnDefinition = "tinyint")
    private boolean pointPolicyStatus;

    private String imgUrl;

    @Column(columnDefinition = "text")
    private String description;

    private String extraBusinessDay;

    private Integer minimumOrderPrice;

    private BigDecimal saveRate;

    private Integer deliveryCharge;

    @OneToOne(mappedBy = "store", cascade = ALL, orphanRemoval = true)
    private StoreMetadata storeMetadata;

    @OneToMany(mappedBy = "store", cascade = ALL, orphanRemoval = true)
    @OrderBy("id asc")
    private List<StoreBusinessDay> storeBusinessDays = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @OrderBy("createdAt desc")
    private List<Writing> writings = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @OrderBy("createdAt desc")
    private List<Review> reviews = new ArrayList<>();


    public Store(Owner owner, StoreRequestDto dto) {
        this.owner = owner;
        this.storeType = StoreType.krTypeOf(dto.getStoreType());
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.extraBusinessDay = dto.getExtraBusinessDay();
        this.phone = dto.getPhone();
        this.isOpen = Boolean.FALSE;
        this.storeStatus = StoreStatus.PENDING;
        this.deliveryStatus = dto.getDeliveryStatus();
        this.deliveryCharge = dto.getDeliveryCharge();
        this.streetAddress = dto.getStreetAddress();
        this.detailedAddress = dto.getDetailedAddress();
        this.lat = new BigDecimal(dto.getLat());
        this.lng = new BigDecimal(dto.getLng());
    }

    //가게 정보 업데이트
    public void update(StoreRequestDto dto) {
        this.storeType = StoreType.krTypeOf(dto.getStoreType());
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.extraBusinessDay = dto.getExtraBusinessDay();
        this.phone = dto.getPhone();
        this.deliveryStatus = dto.getDeliveryStatus();
        this.deliveryCharge = dto.getDeliveryCharge();

        List<StoreBusinessDayRequestDto> storeBusinessDays = dto.getStoreBusinessDays();

        for (int i = 0; i < storeBusinessDays.size(); i++) {
            this.storeBusinessDays.get(i).update(storeBusinessDays.get(i));
        }
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public void updateStoreStatus(StoreStatus storeStatus) {
        this.storeStatus = storeStatus;
    }

    //가게 포인트 정책 업데이트
    public void updatePointPolicy(StorePointPolicyRequestDto dto) {
        this.pointPolicyStatus = dto.getPointPolicyStatus();
        this.saveRate = dto.getSaveRate();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void updateBusinessStatus(boolean isOpen) {
        this.isOpen = isOpen;
    }
}


















