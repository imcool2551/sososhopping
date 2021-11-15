package com.sososhopping.server.common.dto.owner.response;

import com.sososhopping.server.entity.store.Accounting;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreAccountingResponseDto {
    private Long storeId;
    private Long id;
    private Integer amount;
    private String description;
    private String date;

    public StoreAccountingResponseDto(Accounting accounting, Long storeId) {
        this.storeId = storeId;
        this.id = accounting.getId();
        this.amount = accounting.getAmount();
        this.description = accounting.getDescription();
        this.date = accounting.getDate()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
    }
}
