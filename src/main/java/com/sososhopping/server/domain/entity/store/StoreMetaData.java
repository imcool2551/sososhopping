package com.sososhopping.server.domain.entity.store;

import com.sososhopping.server.domain.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "store_metadata")
public class StoreMetaData extends BaseTimeEntity {

    @Id
    @Column(name = "store_id")
    private Long storeId;

    @MapsId("storeId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    @Column(unique = true)
    private String businessNumber;

    @NotNull
    private String representativeName;

    @NotNull
    private String businessName;

    @NotNull
    private LocalDateTime openingDate;
}
