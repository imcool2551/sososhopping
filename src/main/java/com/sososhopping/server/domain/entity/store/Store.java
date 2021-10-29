package com.sososhopping.server.domain.entity.store;

import com.sososhopping.server.domain.entity.BaseTimeEntity;
import com.sososhopping.server.domain.entity.InterestStore;
import com.sososhopping.server.domain.entity.Review;
import com.sososhopping.server.domain.entity.coupon.Coupon;
import com.sososhopping.server.domain.entity.member.Owner;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store extends BaseTimeEntity {

    @Id @GeneratedValue
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

    private String description;

    private String extraBusinessDay;

    @NotNull
    private String phone;

    @NotNull
    private Point location;

    @NotNull
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean businessStatus;

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
    private Boolean pickupStatus;

    @NotNull
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1
            ,name = "delivery_status")
    private Boolean deliveryStatus;

    @Column(columnDefinition = "TINYINT", length = 1
            ,name = "delivery_status")
    private Boolean pointPolicyStatus;

    private Integer minimumOrderPrice;

    private BigDecimal saveRate;

    @NotNull
    private String streetAddress;

    @NotNull
    private String detailedAddress;

    //List
    @OneToMany(mappedBy = "store")
    private List<StoreBusinessDay> storeBusinessDays;

    @OneToMany(mappedBy = "store")
    private List<StoreImage> storeImages = new ArrayList<>();

    @OneToOne(fetch = LAZY, mappedBy = "store")
    private StoreMetaData storeMetaData;

    @OneToMany(mappedBy = "store")
    private List<Accounting> accountings = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<StoreLog> storeLogs = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Writing> writings = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<InterestStore> interestStores = new ArrayList<>();

    @OneToMany
    private List<Review> reviews = new ArrayList<>();
}