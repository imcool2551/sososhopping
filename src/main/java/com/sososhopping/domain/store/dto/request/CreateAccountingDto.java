package com.sososhopping.domain.store.dto.request;

import com.sososhopping.entity.store.Accounting;
import com.sososhopping.entity.store.Store;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CreateAccountingDto {

    @NotNull(message = "금액 필수")
    private Integer amount;

    private String description;

    private LocalDateTime date;

    public Accounting toEntity(Store store) {
        return Accounting.builder()
                .store(store)
                .amount(amount)
                .description(description)
                .date(date)
                .build();
    }
}
