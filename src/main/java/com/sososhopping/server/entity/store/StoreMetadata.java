package com.sososhopping.server.entity.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.sososhopping.server.entity.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreMetadata extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "store_metadata_id")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String businessNumber;

    @NotNull
    private String representativeName;

    @NotNull
    private String businessName;

    @NotNull
    private LocalDateTime openingDate;

    @OneToOne(mappedBy = "storeMetadata")
    private Store store;
}
