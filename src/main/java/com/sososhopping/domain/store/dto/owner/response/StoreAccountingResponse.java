package com.sososhopping.domain.store.dto.owner.response;

import com.sososhopping.entity.store.Accounting;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreAccountingResponse {

    private Long id;
    private Long storeId;
    private int amount;
    private String description;
    private LocalDateTime date;

    public StoreAccountingResponse(Long storeId, Accounting accounting) {
        this.id = accounting.getId();
        this.storeId = storeId;
        this.amount = accounting.getAmount();
        this.description = accounting.getDescription();
        this.date = accounting.getDate();
    }
}
