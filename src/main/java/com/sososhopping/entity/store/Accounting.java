package com.sososhopping.entity.store;

import com.sososhopping.common.dto.owner.request.StoreAccountingRequestDto;
import com.sososhopping.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.*;

@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Accounting extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accounting_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Integer amount;

    @Column(columnDefinition = "TEXT")
    private String description;

    public void update(StoreAccountingRequestDto dto) {
        this.date= LocalDateTime.parse(dto.getDate() + ":00",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        this.amount = dto.getAmount();
        this.description = dto.getDescription();
    }
}