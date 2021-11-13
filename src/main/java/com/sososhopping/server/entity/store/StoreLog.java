package com.sososhopping.server.entity.store;

import com.sososhopping.server.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreLog extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_log_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    @Column(columnDefinition = "TEXT")
    private String description;
}
