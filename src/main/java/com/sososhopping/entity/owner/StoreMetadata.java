package com.sososhopping.entity.owner;

import com.sososhopping.entity.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "store_metadata")
public class StoreMetadata extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "store_metadata_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(unique = true, columnDefinition = "char", length = 10)
    private String businessNumber;

    private String representativeName;

    private String businessName;

    private LocalDateTime openingDate;
}
