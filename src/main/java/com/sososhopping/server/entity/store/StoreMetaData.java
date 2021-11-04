package com.sososhopping.server.entity.store;

import com.sososhopping.server.common.dto.owner.request.StoreMetaDataRequestDto;
import com.sososhopping.server.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

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
}
