package com.sososhopping.server.common.dto.user.request.order;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateCartDto {

    @Valid
    @Size(min = 1)
    private List<UpdateCartItemDto> requests = new ArrayList<>();

    @Data
    public static class UpdateCartItemDto {
        @NotNull
        private Long itemId;

        @Min(1)
        private Integer quantity;
    }
}
