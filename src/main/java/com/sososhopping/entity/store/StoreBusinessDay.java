package com.sososhopping.entity.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"store_id", "day"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreBusinessDay {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "store_business_day_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(EnumType.STRING)
    private Day day;

    @Column(columnDefinition = "tinyint")
    private boolean isOpen;

    @Column(columnDefinition = "char", length = 4)
    private String openTime;

    @Column(columnDefinition = "char", length = 4)
    private String closeTime;

    @Builder
    public StoreBusinessDay(Store store, Day day, boolean isOpen, String openTime, String closeTime) {
        this.store = store;
        this.day = day;
        this.isOpen = isOpen;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}