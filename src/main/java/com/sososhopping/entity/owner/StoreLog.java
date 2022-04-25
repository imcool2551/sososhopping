package com.sososhopping.entity.owner;

import com.sososhopping.entity.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreLog extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "store_log_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    @Column(columnDefinition = "text")
    private String description;

    @Builder
    public StoreLog(Store store, StoreStatus storeStatus, String description) {
        this.store = store;
        this.storeStatus = storeStatus;
        this.description = description;
    }
}
