package com.sososhopping.entity.owner;

import com.sososhopping.common.dto.owner.request.StorePointPolicyRequestDto;
import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.orders.Item;
import com.sososhopping.entity.user.Review;
import lombok.AccessLevel;
import lombok.Builder;
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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "store_metadata_id")
    private StoreMetadata storeMetadata;

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

    @Builder
    public Store(Owner owner, StoreMetadata storeMetadata, String name, StoreType storeType, String phone,
                 BigDecimal lat, BigDecimal lng, String streetAddress,
                 String detailedAddress, StoreStatus storeStatus,
                 boolean isOpen, boolean pickupStatus, boolean deliveryStatus,
                 boolean pointPolicyStatus, String imgUrl, String description,
                 String extraBusinessDay, Integer minimumOrderPrice,
                 BigDecimal saveRate, Integer deliveryCharge) {

        this.owner = owner;
        this.storeMetadata = storeMetadata;
        this.name = name;
        this.storeType = storeType;
        this.phone = phone;
        this.lat = lat;
        this.lng = lng;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.storeStatus = storeStatus;
        this.isOpen = isOpen;
        this.pickupStatus = pickupStatus;
        this.deliveryStatus = deliveryStatus;
        this.pointPolicyStatus = pointPolicyStatus;
        this.imgUrl = imgUrl;
        this.description = description;
        this.extraBusinessDay = extraBusinessDay;
        this.minimumOrderPrice = minimumOrderPrice;
        this.saveRate = saveRate;
        this.deliveryCharge = deliveryCharge;
    }

    public boolean belongsTo(Owner owner) {
        return this.owner == owner;
    }

    public void updateOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void updateStoreStatus(StoreStatus storeStatus) {
        this.storeStatus = storeStatus;
    }

    public void updatePointPolicy(StorePointPolicyRequestDto dto) {
        this.pointPolicyStatus = dto.getPointPolicyStatus();
        this.saveRate = dto.getSaveRate();
    }
}


















