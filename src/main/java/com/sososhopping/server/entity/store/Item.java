package com.sososhopping.server.entity.store;

import com.sososhopping.server.common.dto.owner.request.StoreItemRequestDto;
import com.sososhopping.server.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Builder
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    private String name;

    private String description;

    private String purchaseUnit;

    private String imgUrl;

    @NotNull
    private Integer price;

    @NotNull
    @Type(type = "numeric_boolean")
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean saleStatus;

    // 연관 관계 편의 메서드
    public void setStore(Store store) {
        this.store = store;
        this.store.getItems().add(this);
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void update(StoreItemRequestDto dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.purchaseUnit = dto.getPurchaseUnit();
        this.price = dto.getPrice();
        this.saleStatus = dto.getSaleStatus();
    }
}
