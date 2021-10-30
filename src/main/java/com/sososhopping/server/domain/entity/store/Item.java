package com.sososhopping.server.domain.entity.store;

import com.sososhopping.server.domain.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue
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
}
