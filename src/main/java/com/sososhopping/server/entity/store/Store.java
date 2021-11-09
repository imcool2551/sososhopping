package com.sososhopping.server.entity.store;

import com.sososhopping.server.common.dto.owner.request.StoreBusinessDayRequestDto;
import com.sososhopping.server.common.dto.owner.request.StorePointPolicyRequestDto;
import com.sososhopping.server.common.dto.owner.request.StoreRequestDto;
import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.member.InterestStore;
import com.sososhopping.server.entity.member.Review;
import com.sososhopping.server.entity.coupon.Coupon;
import com.sososhopping.server.entity.member.Owner;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @NotNull
    private String name;

    private String imgUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String extraBusinessDay;

    @NotNull
    @Column(columnDefinition = "char")
    private String phone;

    @NotNull
    private Point location;

    @NotNull
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean businessStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    @NotNull
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1
            , name = "local_currency_status")
    private Boolean localCurrencyStatus;

    @NotNull
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1
            ,name = "pickup_status")
    private Boolean pickupStatus = true;

    @NotNull
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1
            , name = "delivery_status")
    private Boolean deliveryStatus;

    @NotNull
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1
            , name = "point_policy_status")
    private Boolean pointPolicyStatus = false;

    private BigDecimal saveRate;

    @NotNull
    private String streetAddress;

    @NotNull
    private String detailedAddress;

    @OneToOne(mappedBy = "store", fetch = LAZY, cascade = ALL,
            orphanRemoval = true)
    private StoreMetaData storeMetaData;

    //List
    @OneToMany(mappedBy = "store", cascade = ALL,
            orphanRemoval = true)
    @OrderBy("id asc")
    private List<StoreBusinessDay> storeBusinessDays = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = ALL,
            orphanRemoval = true)
    private List<StoreImage> storeImages = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Accounting> accountings = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<StoreLog> storeLogs = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @OrderBy("createdAt desc")
    private List<Writing> writings = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = ALL)
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<InterestStore> interestStores = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @OrderBy("createdAt desc")
    private List<Review> reviews = new ArrayList<>();

    // 연관 관계 편의 메서드
    public void setOwner(Owner owner) {
        if (this.owner != null) {
            this.owner.getStores().remove(this);
        }
        this.owner = owner;
        this.owner.getStores().add(this);
    }

    public Store(Owner owner, StoreRequestDto dto, Point location) {
        this.owner = owner;
        this.storeType = StoreType.valueOf(dto.getStoreType());
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.extraBusinessDay = dto.getExtraBusinessDay();
        this.phone = dto.getPhone();
        this.businessStatus = Boolean.FALSE;
        this.storeStatus = StoreStatus.PENDING;
        this.localCurrencyStatus = dto.getLocalCurrencyStatus();
        this.deliveryStatus = dto.getDeliveryStatus();
        this.streetAddress = dto.getStreetAddress();
        this.detailedAddress = dto.getDetailedAddress();
        this.location = location;
    }

    //가게 정보 업데이트
    public void update(StoreRequestDto dto) {
        this.storeType = StoreType.valueOf(dto.getStoreType());
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.extraBusinessDay = dto.getExtraBusinessDay();
        this.phone = dto.getPhone();
        this.localCurrencyStatus = dto.getLocalCurrencyStatus();
        this.deliveryStatus = dto.getDeliveryStatus();

        List<StoreBusinessDayRequestDto> storeBusinessDays = dto.getStoreBusinessDays();

        for (int i = 0; i < storeBusinessDays.size(); i++) {
            this.storeBusinessDays.get(i).update(storeBusinessDays.get(i));
        }
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    //가게 포인트 정책 업데이트
    public void updatePointPolicy(StorePointPolicyRequestDto dto) {
        this.pointPolicyStatus = dto.getPointPolicyStatus();
        this.saveRate = dto.getSaveRate();
    }
}
