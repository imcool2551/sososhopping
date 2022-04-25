package com.sososhopping.entity.owner;

import com.sososhopping.common.dto.owner.request.StoreAccountingRequestDto;
import com.sososhopping.entity.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.LAZY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accounting extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accounting_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private int amount;

    @Column(columnDefinition = "text")
    private String description;

    private LocalDateTime date;

    @Builder
    public Accounting(Store store, int amount, String description, LocalDateTime date) {
        this.store = store;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public void update(StoreAccountingRequestDto dto) {
        this.date= LocalDateTime.parse(dto.getDate() + ":00",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        this.amount = dto.getAmount();
        this.description = dto.getDescription();
    }
}