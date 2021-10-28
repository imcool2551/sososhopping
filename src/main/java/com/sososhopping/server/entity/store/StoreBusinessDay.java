package com.sososhopping.server.entity.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalTime;

import static javax.persistence.FetchType.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"store_id", "day"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreBusinessDay implements Serializable {

    @Id @GeneratedValue
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
    private Boolean isOpen;

    private LocalTime openTime;

    private LocalTime closeTime;
}
