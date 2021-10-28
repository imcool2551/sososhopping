package com.sososhopping.server.entity.store;


import com.vividsolutions.jts.geom.Point;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.member.owner.Owner;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private boolean businessStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    @NotNull
    private Boolean localCurrencyStatus;

    @NotNull
    private Boolean pickupStatus;

    @NotNull
    private Boolean deliveryStatus;

    private Integer minimumOrderPrice;

    private Double saveRate;

    @NotNull
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "store_metadata_id", unique = true)
    private StoreMetadata storeMetadata;

    @NotNull
    private String streetAddress;

    @NotNull
    private String detailedAddress;

    @OneToMany(mappedBy = "store")
    private List<StoreBusinessDay> storeBusinessDays;
}
