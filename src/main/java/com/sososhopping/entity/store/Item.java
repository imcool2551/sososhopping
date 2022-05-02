package com.sososhopping.entity.store;

import com.sososhopping.entity.common.BaseTimeEntity;
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

    public boolean available(Store store) {
        return belongsTo(store) && onSale;
    }

    public boolean belongsTo(Store store) {
        return this.store == store;
    }
}
