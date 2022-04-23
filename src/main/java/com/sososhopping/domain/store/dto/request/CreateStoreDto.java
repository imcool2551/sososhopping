package com.sososhopping.domain.store.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateStoreDto {

    @NotNull(message = "이름 필수")
    @NotBlank(message = "이름 필수")
    private String name;
}
