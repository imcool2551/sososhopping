package com.sososhopping.entity.store;

import com.sososhopping.common.dto.owner.request.StoreMetaDataRequestDto;
import com.sososhopping.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    @Column(unique = true, columnDefinition = "char")
    private String businessNumber;

    @NotNull
    private String representativeName;

    @NotNull
    private String businessName;

    @NotNull
    private String openingDate;

    public StoreMetaData(Store store, StoreMetaDataRequestDto dto) {
        this.store = store;
        this.businessNumber = dto.getBusinessNumber();
        this.representativeName = dto.getRepresentativeName();
        this.businessName = dto.getBusinessName();
        this.openingDate = dto.getOpeningDate();
    }
}
