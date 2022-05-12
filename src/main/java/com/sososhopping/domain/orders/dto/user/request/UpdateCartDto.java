package com.sososhopping.domain.orders.dto.user.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateCartDto {

    @NotNull
    @Size(min = 1)
    private List<AddCartItemDto> requests;
}
