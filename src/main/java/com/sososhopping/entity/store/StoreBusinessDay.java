package com.sososhopping.entity.store;

import com.sososhopping.common.dto.owner.request.StoreBusinessDayRequestDto;
import lombok.AccessLevel;
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

    public StoreBusinessDay(Store store, StoreBusinessDayRequestDto dto) {
        this.store = store;
        this.day = Day.krDayOf(dto.getDay());
        this.isOpen = dto.getIsOpen();
        this.openTime = dto.getOpenTime();
        this.closeTime = dto.getCloseTime();
    }

    public void update(StoreBusinessDayRequestDto dto) {
        this.isOpen = dto.getIsOpen();
        this.openTime = dto.getOpenTime();
        this.closeTime = dto.getCloseTime();
    }
}