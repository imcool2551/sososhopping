package com.sososhopping.server.entity.store;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Builder
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"store_id", "day"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreBusinessDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_business_day_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Day day;

    @NotNull
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean isOpen;

    @Column(columnDefinition = "char")
    private String openTime;

    @Column(columnDefinition = "char")
    private String closeTime;

    // 연관 관계 편의 메서드
    public void setStore(Store store) {
        this.store = store;
        this.store.getStoreBusinessDays().add(this);
    }
}