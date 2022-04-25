package com.sososhopping.entity.store;

import com.sososhopping.common.dto.owner.request.StoreItemRequestDto;
import com.sososhopping.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String name;

    private String description;

    private String purchaseUnit;

    private String imgUrl;

    private int price;

    @Column(columnDefinition = "tinyint")
    private boolean onSale;

    @Builder
    public Item(Store store, String name, String description, String purchaseUnit, String imgUrl, int price, boolean onSale) {
        this.store = store;
        this.name = name;
        this.description = description;
        this.purchaseUnit = purchaseUnit;
        this.imgUrl = imgUrl;
        this.price = price;
        this.onSale = onSale;
    }

    // 연관 관계 편의 메서드
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void update(StoreItemRequestDto dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.purchaseUnit = dto.getPurchaseUnit();
        this.price = dto.getPrice();
        this.onSale = dto.getSaleStatus();
    }

    // Business Logic
    public boolean canBeProvidedBy(Store store) {
        return this.store == store && onSale;
    }
}
