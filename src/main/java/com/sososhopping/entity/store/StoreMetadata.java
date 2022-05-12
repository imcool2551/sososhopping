package com.sososhopping.entity.store;

import com.sososhopping.entity.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_metadata_id")
    private Long id;

    @Column(unique = true, columnDefinition = "char", length = 10)
    private String businessNumber;

    private String representativeName;

    private String businessName;

    private LocalDateTime openingDate;

    @Builder
    public StoreMetadata(String businessNumber, String representativeName,
                         String businessName, LocalDateTime openingDate) {

        this.businessNumber = businessNumber;
        this.representativeName = representativeName;
        this.businessName = businessName;
        this.openingDate = openingDate;
    }
}
