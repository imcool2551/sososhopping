package com.sososhopping.server.entity.store;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_image_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    @Column(length = 512)
    private String imgUrl;

    // 생성자 + 빌더
    @Builder
    public StoreImage(Store store, String imgUrl) {
        setStore(store);
        this.imgUrl = imgUrl;
    }

    // 연관 관계 편의 메서드
    private void setStore(Store store) {
        this.store = store;
        this.store.getStoreImages().add(this);
    }
}